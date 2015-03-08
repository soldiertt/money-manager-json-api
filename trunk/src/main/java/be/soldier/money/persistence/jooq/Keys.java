/**
 * This class is generated by jOOQ
 */
package be.soldier.money.persistence.jooq;

/**
 * A class modelling foreign key relationships between tables of the <code>money</code> 
 * schema
 */
@javax.annotation.Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.5.1"
	},
	comments = "This class is generated by jOOQ"
)
@java.lang.SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.AccountRecord, java.lang.Integer> IDENTITY_ACCOUNT = Identities0.IDENTITY_ACCOUNT;
	public static final org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.LabelRecord, java.lang.Integer> IDENTITY_LABEL = Identities0.IDENTITY_LABEL;
	public static final org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.NotesRecord, java.lang.Integer> IDENTITY_NOTES = Identities0.IDENTITY_NOTES;
	public static final org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.TxRecord, java.lang.Integer> IDENTITY_TX = Identities0.IDENTITY_TX;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.AccountRecord> KEY_ACCOUNT_PRIMARY = UniqueKeys0.KEY_ACCOUNT_PRIMARY;
	public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.AccountRecord> KEY_ACCOUNT_ACCOUNT_NUMBER = UniqueKeys0.KEY_ACCOUNT_ACCOUNT_NUMBER;
	public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.LabelRecord> KEY_LABEL_PRIMARY = UniqueKeys0.KEY_LABEL_PRIMARY;
	public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.NotesRecord> KEY_NOTES_PRIMARY = UniqueKeys0.KEY_NOTES_PRIMARY;
	public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.TxRecord> KEY_TX_PRIMARY = UniqueKeys0.KEY_TX_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.LabelRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> LABEL_IBFK_1 = ForeignKeys0.LABEL_IBFK_1;
	public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> TX_IBFK_1 = ForeignKeys0.TX_IBFK_1;
	public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> TX_IBFK_2 = ForeignKeys0.TX_IBFK_2;
	public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.LabelRecord> TX_IBFK_3 = ForeignKeys0.TX_IBFK_3;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends org.jooq.impl.AbstractKeys {
		public static org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.AccountRecord, java.lang.Integer> IDENTITY_ACCOUNT = createIdentity(be.soldier.money.persistence.jooq.tables.Account.ACCOUNT, be.soldier.money.persistence.jooq.tables.Account.ACCOUNT.ID);
		public static org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.LabelRecord, java.lang.Integer> IDENTITY_LABEL = createIdentity(be.soldier.money.persistence.jooq.tables.Label.LABEL, be.soldier.money.persistence.jooq.tables.Label.LABEL.ID);
		public static org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.NotesRecord, java.lang.Integer> IDENTITY_NOTES = createIdentity(be.soldier.money.persistence.jooq.tables.Notes.NOTES, be.soldier.money.persistence.jooq.tables.Notes.NOTES.ID);
		public static org.jooq.Identity<be.soldier.money.persistence.jooq.tables.records.TxRecord, java.lang.Integer> IDENTITY_TX = createIdentity(be.soldier.money.persistence.jooq.tables.Tx.TX, be.soldier.money.persistence.jooq.tables.Tx.TX.ID);
	}

	private static class UniqueKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.AccountRecord> KEY_ACCOUNT_PRIMARY = createUniqueKey(be.soldier.money.persistence.jooq.tables.Account.ACCOUNT, be.soldier.money.persistence.jooq.tables.Account.ACCOUNT.ID);
		public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.AccountRecord> KEY_ACCOUNT_ACCOUNT_NUMBER = createUniqueKey(be.soldier.money.persistence.jooq.tables.Account.ACCOUNT, be.soldier.money.persistence.jooq.tables.Account.ACCOUNT.ACCOUNT_NUMBER);
		public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.LabelRecord> KEY_LABEL_PRIMARY = createUniqueKey(be.soldier.money.persistence.jooq.tables.Label.LABEL, be.soldier.money.persistence.jooq.tables.Label.LABEL.ID);
		public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.NotesRecord> KEY_NOTES_PRIMARY = createUniqueKey(be.soldier.money.persistence.jooq.tables.Notes.NOTES, be.soldier.money.persistence.jooq.tables.Notes.NOTES.ID);
		public static final org.jooq.UniqueKey<be.soldier.money.persistence.jooq.tables.records.TxRecord> KEY_TX_PRIMARY = createUniqueKey(be.soldier.money.persistence.jooq.tables.Tx.TX, be.soldier.money.persistence.jooq.tables.Tx.TX.ID);
	}

	private static class ForeignKeys0 extends org.jooq.impl.AbstractKeys {
		public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.LabelRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> LABEL_IBFK_1 = createForeignKey(be.soldier.money.persistence.jooq.Keys.KEY_ACCOUNT_PRIMARY, be.soldier.money.persistence.jooq.tables.Label.LABEL, be.soldier.money.persistence.jooq.tables.Label.LABEL.DEFAULT_RECIPIENT_ID);
		public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> TX_IBFK_1 = createForeignKey(be.soldier.money.persistence.jooq.Keys.KEY_ACCOUNT_PRIMARY, be.soldier.money.persistence.jooq.tables.Tx.TX, be.soldier.money.persistence.jooq.tables.Tx.TX.FROM_ACCOUNT_ID);
		public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.AccountRecord> TX_IBFK_2 = createForeignKey(be.soldier.money.persistence.jooq.Keys.KEY_ACCOUNT_PRIMARY, be.soldier.money.persistence.jooq.tables.Tx.TX, be.soldier.money.persistence.jooq.tables.Tx.TX.RECIPIENT_ID);
		public static final org.jooq.ForeignKey<be.soldier.money.persistence.jooq.tables.records.TxRecord, be.soldier.money.persistence.jooq.tables.records.LabelRecord> TX_IBFK_3 = createForeignKey(be.soldier.money.persistence.jooq.Keys.KEY_LABEL_PRIMARY, be.soldier.money.persistence.jooq.tables.Tx.TX, be.soldier.money.persistence.jooq.tables.Tx.TX.LABEL_ID);
	}
}
