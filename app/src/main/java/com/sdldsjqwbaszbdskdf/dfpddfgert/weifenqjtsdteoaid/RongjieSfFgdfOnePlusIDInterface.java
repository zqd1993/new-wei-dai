package com.sdldsjqwbaszbdskdf.dfpddfgert.weifenqjtsdteoaid;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;

public interface RongjieSfFgdfOnePlusIDInterface extends IInterface {
    public static abstract class up extends Binder implements RongjieSfFgdfOppoIDInterface {

        public static class down implements RongjieSfFgdfOppoIDInterface {
            public IBinder iBinder;

            public down(IBinder ib) {
                iBinder = ib;
            }

            public String getSerID(String str1, String str2, String str3) {
                String res = null;
                Parcel obtain = Parcel.obtain();
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken("com.heytap.openid.IOpenID");
                    obtain.writeString(str1);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    iBinder.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                    res = obtain2.readString();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    obtain.recycle();
                    obtain2.recycle();
                }
                return res;
            }

            @Override
            public IBinder asBinder() {
                return iBinder;
            }
        }

        public static RongjieSfFgdfOnePlusIDInterface genInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("com.heytap.openid.IOpenID");
            if (iInterface == null || !(iInterface instanceof RongjieSfFgdfOnePlusIDInterface)) {
                return new up.down(iBinder);
            } else {
                return (RongjieSfFgdfOnePlusIDInterface) iInterface;
            }
        }
    }
}
