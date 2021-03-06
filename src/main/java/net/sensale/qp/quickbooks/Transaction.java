/**
 * Copyright 2014 Brian Sensale
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sensale.qp.quickbooks;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a single Transaction in an IIF File.
 *
 */
public class Transaction {

    TransactionLine mTransLine;
    List<SplitLine> mSplitLines;
    Account mAccount;
    String mName;
    QBClass mClass;

    private final String sNewLine = System.getProperty("line.separator");
    private final String sSeasubRegEx = ".*Season Subscription.*";
    private final String sShowMatch = ".*(?<=icket[s]? for )(.*?) on \\d{1,2}\\/.*|.*icket.*, (.*) \\- \\d{1,2}\\/.*";
    private final String sFunRaiserMatch = ".*Fundrais.*";
    
    Transaction() {
        // Default constructor for tests
    }

    /**
     * Initializes a Transaction with the given Transaction Line and Split lines.
     * 
     * @param pTransLine
     * @param pSplitLines
     */
    public Transaction(String pTransLine, String[] pSplitLines) {
        createTransLine(pTransLine);
        createSplitLines(pSplitLines);
    }

    /**
     * Creates a set of split lines from the given array of Split Lines.  Also creates a new line if
     *   there is a donation.
     *   
     * @param pSplitLines
     */
    void createSplitLines(String[] pSplitLines) {
        mSplitLines = new ArrayList<SplitLine>();
        for (String line : pSplitLines) {
            mSplitLines.add(createSplitLine(line));
        }
        Double donationValue = mTransLine.mMemo.getDonationValue();
        if (donationValue != null) {
            //boolean isFundraiser = StringUtils.stripQuotes(mTransLine.mMemo.toString()).matches(sFunRaiserMatch);
            //Name name = isFundraiser ? new Name("Fundraiser") : mTransLine.mName;
            //QBClass qbclass = isFundraiser ? QBClass.FUNDRAISER : QBClass.HOUSE;
            Name name = mTransLine.mName;
            QBClass qbclass = QBClass.HOUSE;
            SplitLine donation = new SplitLine(
                    mTransLine.mDate,
                    Account.DONATIONS,
                    name,
                    qbclass,
                    new Amount(donationValue),
                    mTransLine.mMemo);
            mSplitLines.add(donation);
        }
    }

    /**
     * Creates a single split line from a String.
     * 
     * @param pSplitLine
     * @return
     */
    SplitLine createSplitLine(String pSplitLine) {
        String[] columns = pSplitLine.split("\t");
        if (columns.length < 5) {
            throw new IllegalArgumentException(
                    "Couldn't split SPL line into more than 5 columns by tabs, found: "
                            + columns.length + " " + pSplitLine);
        }
        if (!"SPL".equals(columns[0])) {
            throw new IllegalArgumentException("Line didn't start with SPL: " + pSplitLine);
        }
        Account account = getAccount(columns[2]);
        Amount amt = new Amount(columns[4]);
        if (account != Account.PAYPAL_EXPENSE && mTransLine.mMemo.getDonationValue() != null) {
            amt.mValue -= mTransLine.mMemo.getDonationValue();
        }
        SplitLine result = new SplitLine(
        		new Date(columns[1]), // columns[1] = "2/2/2008"
                account,              // columns[2] = "Other Income/Expense" generated based on
                                      // current value, and transaction
                new Name(mName),      // columns[3] = depends on transaction. Name, Fundraiser
                                      // of the name of the show
                mClass,               // This is generated, either House, show or Fundraiser
                amt,                  // columns[4] = -40.00
                mTransLine.mMemo);    // Use the memo from the transaction

        return result;
    }

    /**
     * Converts the String into an Account, stripping quotes, and converting "Other Expenses
     * @param pCurrentValue
     * @return
     */
    public Account getAccount(String pCurrentValue) {
        String value = StringUtils.stripQuotes(pCurrentValue);
        if(mClass == QBClass.TRANSFER) {
        	return Account.CHECKING;
        }
        if ("Other Expenses".equalsIgnoreCase(value)) {
            return Account.PAYPAL_EXPENSE;
        } else
            return mAccount;
    }

    /**
     * Creates a TransactionLine from the String, populating the Transaction Object as it goes.
     * 
     * @param pTransLine
     */
    void createTransLine(String pTransLine) {
        String[] columns = pTransLine.split("\t");
        if (columns.length < 6) {
            throw new IllegalArgumentException(
                    "Couldn't split TRNS line into at least 6 columns by tabs, found: "
                            + columns.length + " " + pTransLine);
        }
        if (!"TRNS".equals(columns[0])) {
            throw new IllegalArgumentException("Line didn't start with TRNS: " + pTransLine);
        }
        QBClass headerClass = parseClassAndDetectDonation(columns[4]);
        if(headerClass != QBClass.TRANSFER && columns.length < 7) {
        	throw new IllegalArgumentException(
        			"TRNS line was missing the memo field, but this wasn't a transfer");
        }
        String memo;
        if(headerClass == QBClass.TRANSFER) {
        	memo = "Transfer to bank account";
        }
        else {	
        	memo = parseMemo(columns[6], columns[3]);
        }
        mTransLine = new TransactionLine(new Date(columns[1]), // columns[1] = "10/1/2009"
                Account.PAYPAL,                                // columns[2] = "Paypal", always use Paypal
                new Name(columns[3]),                          // columns[3] = "Person Lastname"
                headerClass,                                   // columns[4] = "Web Accept Checkout Payment"
                new Amount(columns[5]),                        // columns[5] = 44.44 (total amount of transaction 
                new Memo(memo));                               // columns[6] = "Descriptive memo"
    }

    /**
     * Checks that we can handle the class of transaction.  Today that is "Checkout Payment",
     * "Payment Completed" or "Donation Payment"
     * @param pClass
     */
    QBClass parseClassAndDetectDonation(String pClass) {
        String input = StringUtils.stripQuotes(pClass);
        if(input == null) {
            throw new IllegalArgumentException("Class String was null");
        }
        else if (input.endsWith("Checkout Payment")) {
            return QBClass.WEB_PAYMENT;
        }
        else if (input.equals("Donation Payment")) {
            mClass = QBClass.DONATION;
            return QBClass.DONATION;
        }
        else if (input.equals("General Withdrawal")) {
        	mClass = QBClass.TRANSFER;
        	return QBClass.TRANSFER;
        }
        else if ("Payment Completed".equals(input)) {
            throw new PaymentCompletedClassException(
                    "You can probably skip this transaction because it is a payment completed line");
        } else
            throw new UnsupportedClassException("Unknown transaction class: " + pClass);
    }

    /**
     * Parse the memo to determine the Account, Class and Name of the show for the transaction.
     * 
     * @param pMemo The memo section of the line
     * @param pName The name section of the line.
     */
    String parseMemo(String pMemo, String pName) {
        String memo = StringUtils.stripQuotes(pMemo);
        if (memo.matches(sSeasubRegEx)) {
            mAccount = Account.SEA_SUB;
            mName = pName;
            mClass = QBClass.HOUSE;
            return pMemo;
        }
        else if (memo.matches(sShowMatch)) {
            mAccount = Account.DOOR_SALES;
            Pattern p = Pattern.compile(sShowMatch);
            Matcher m = p.matcher(memo);
            m.matches();
            String group1 = m.group(1);
            String group2 = m.group(2);
            mName = ShowTransformer.getInstance().getShow(group1 != null ? group1 : group2);
            mClass = memo.matches(sFunRaiserMatch)? QBClass.FUNDRAISER : QBClass.SHOW;
            return pMemo;
        }
        else if (memo.matches(sFunRaiserMatch)) {
            mAccount = Account.DOOR_SALES;
            mName = QBClass.FUNDRAISER.toString();
            mClass = QBClass.FUNDRAISER;
            return pMemo;
        }
        else if (mClass == QBClass.DONATION) {
            mAccount = Account.DONATIONS;
            mName = QBClass.FUNDRAISER.toString();
            mClass = QBClass.FUNDRAISER;
            //Hack for 80 for 80
            return "80 for 80";
        }
        else {
            throw new RuntimeException("Could not match the memo with any of the patterns: " + memo);
        }
    }

    /**
     * @return A string representation of the value to be written to an IIF File for this Transaction.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(mTransLine.toString());
        sb.append(sNewLine);
        for (SplitLine splitLine : mSplitLines) {
            sb.append(splitLine.toString());
            sb.append(sNewLine);
        }
        sb.append("ENDTRNS");
        return sb.toString();
    }
}
