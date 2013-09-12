package com.jonas.smssender;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class DBHelper extends SQLiteOpenHelper {

	public static final String springTbName = "Spring";
	public static final String valentineTbName = "Valentine";
	public static final String nationTbName = "Nation";
	public static final String ID = "_id";
	public static final String MESSAGE = "message";

	public DBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		// Create Spring table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + springTbName + " (" + ID
				+ " INTEGER PRIMARY KEY," + MESSAGE + " VARCHAR)");
		// Insert Data
		db
				.execSQL("INSERT INTO "
						+ springTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(1,'�µ�1�꿪ʼ��ף���½�2��3������4���紺������5��6ɫ��7���ͷף�ż��8��С�ƣ������׵�9������!�������10��10���ף����ף�´�����!')");
		db
				.execSQL("INSERT INTO "
						+ springTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(2,'����������죬����ĽŲ�����ף��������������������п��ֵ�������������ƽ�����紺��ĽŲ���������!������ʵ������Զ����ͬ��!')");
		db.execSQL("INSERT INTO " + springTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES " + "(3,'���¿�����ȥ���䣬ȴ�ʲ�ȥ����һ·���µĻ���Ц�ף���´����֣����갲����')");
		db
				.execSQL("INSERT INTO "
						+ springTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(4,'������˵�������������!����΢΢Ц��ϲ��Χ����!�������㿪���������С���������������!ϲ��!ϲ��!һ��ƽ������!')");
		db
				.execSQL("INSERT INTO "
						+ springTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(5,'�´�����ϲ�¶࣬�ϼ���Բ�Ҹ���;����������Ѷ࣬���彡�����ֶ�;һ��˳�������࣬���꼪�������;ף�����¶�!��!��!')");
		// Create Valentine table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + valentineTbName + " (" + ID
				+ " INTEGER PRIMARY KEY," + MESSAGE + " VARCHAR)");
		// Insert Data
		db
				.execSQL("INSERT INTO "
						+ valentineTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(1,'��������ڲ�ͬ�����˽���Ұ�����һ��һ�����������ָ��,��Ȼһ��ƽƽ����,ͬ���ʿ�,��Ը��ԶΪ�㵲�����깲�ȳ���ĺĺ�����˽ڿ��֣�')");
		db.execSQL("INSERT INTO " + valentineTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(2,'�������㣬����ģ���Ȼ���㣬Ҳ����ġ����˽�����Ұ���ݰ�����丵�ף������ϵ�ף�����Ҹ���������Զ��')");
		db.execSQL("INSERT INTO " + valentineTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(3,'��ϣ��С·û�о�ͷ��������������һֱ����ȥ�������ǹ�ͬ�����Ժ�������ÿһ�����˽ڣ�ף���տ��֣�')");
		db.execSQL("INSERT INTO " + valentineTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(4,'�����İ���ض������ſ��ѣ�ֻ���ڿ����в����ھ��Ī���ϲ�á������ҵ�Ψһ�����˽ڿ��֣� ')");
		db
				.execSQL("INSERT INTO "
						+ valentineTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(5,'�Լ���õ�壬���ˣ��ᱻ��Ц�ģ����ɿ��������ˣ����֣������������ˣ�û�����ˣ����˽ڣ�Ըû�����˵������һ��ȹ��������˽ڣ�')");
		// Create Nation table
		db.execSQL("CREATE TABLE IF NOT EXISTS " + nationTbName + " (" + ID
				+ " INTEGER PRIMARY KEY," + MESSAGE + " VARCHAR)");
		// Insert Data
		db.execSQL("INSERT INTO " + nationTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(1,'�����������ͬ��,ף�����ɩ���������ƽ�Ҹ����������տ���,���彡��,�Ҹ���Զ��')");
		db.execSQL("INSERT INTO " + nationTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(2,'����ڵ���,������ףԸ�����е����������,���ĵ�ʱ����Զ���ڡ��ϵ۴͸�����İ�������ġ�')");
		db
				.execSQL("INSERT INTO "
						+ nationTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(3,'�����л�,��ҵǧ�˾���;��Ŀ����,���Ļ��������� �������,�ʵ���Ի����ȥ����������ëǮ,����δ��,�������첻׼��ʺ,��ʺ��׼��ֽ,��ֽ��������,ֱ������Ϊֹ!�մ�,��ֽ!')");
		db.execSQL("INSERT INTO " + nationTbName + "(" + ID + "," + MESSAGE
				+ ") VALUES "
				+ "(4,'�ո������������Ц��,��ӭ���˹����ϲ��!Ը������˫��ϲ��İ�Χ����Զ�Ҹ�������!! ')");
		db
				.execSQL("INSERT INTO "
						+ nationTbName
						+ "("
						+ ID
						+ ","
						+ MESSAGE
						+ ") VALUES "
						+ "(5,'��ʢʱ��,ɽˮ��Ծʫ����;������ͷ,����Ц������ ����˪�ɵ���һ˿����,��д��յ���һƬ�»�,�����������һĨ����,Եϵ��������һ����Ե������ѽ�,�����������!')");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		// Update TB
		db.execSQL("DROP TABLE IF EXISTS " + springTbName);
		onCreate(db);
		db.execSQL("DROP TABLE IF EXISTS " + valentineTbName);
		onCreate(db);
		db.execSQL("DROP TABLE IF EXISTS " + nationTbName);
		onCreate(db);

	}

}
