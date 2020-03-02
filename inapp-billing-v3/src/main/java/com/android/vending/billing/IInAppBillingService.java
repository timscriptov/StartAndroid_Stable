package com.android.vending.billing;

public interface IInAppBillingService extends android.os.IInterface
{
    public static class Default implements com.android.vending.billing.IInAppBillingService
    {
        @Override public int isBillingSupported(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException
        {
            return 0;
        }

        @Override public android.os.Bundle getSkuDetails(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle skusBundle) throws android.os.RemoteException
        {
            return null;
        }

        @Override public android.os.Bundle getBuyIntent(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException
        {
            return null;
        }

        @Override public android.os.Bundle getPurchases(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken) throws android.os.RemoteException
        {
            return null;
        }

        @Override public int consumePurchase(int apiVersion, java.lang.String packageName, java.lang.String purchaseToken) throws android.os.RemoteException
        {
            return 0;
        }

        @Override public int stub(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException
        {
            return 0;
        }

        @Override public android.os.Bundle getBuyIntentToReplaceSkus(int apiVersion, java.lang.String packageName, java.util.List<java.lang.String> oldSkus, java.lang.String newSku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException
        {
            return null;
        }

        @Override public android.os.Bundle getBuyIntentExtraParams(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload, android.os.Bundle extraParams) throws android.os.RemoteException
        {
            return null;
        }

        @Override public android.os.Bundle getPurchaseHistory(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken, android.os.Bundle extraParams) throws android.os.RemoteException
        {
            return null;
        }

        @Override public int isBillingSupportedExtraParams(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle extraParams) throws android.os.RemoteException
        {
            return 0;
        }
        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }
    public static abstract class Stub extends android.os.Binder implements com.android.vending.billing.IInAppBillingService
    {
        private static final java.lang.String DESCRIPTOR = "com.android.vending.billing.IInAppBillingService";
        public Stub()
        {
            this.attachInterface(this, DESCRIPTOR);
        }

        public static com.android.vending.billing.IInAppBillingService asInterface(android.os.IBinder obj)
        {
            if ((obj==null)) {
                return null;
            }
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin!=null)&&(iin instanceof com.android.vending.billing.IInAppBillingService))) {
                return ((com.android.vending.billing.IInAppBillingService)iin);
            }
            return new com.android.vending.billing.IInAppBillingService.Stub.Proxy(obj);
        }
        @Override public android.os.IBinder asBinder()
        {
            return this;
        }
        @Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
        {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code)
            {
                case INTERFACE_TRANSACTION:
                {
                    reply.writeString(descriptor);
                    return true;
                }
                case TRANSACTION_isBillingSupported:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _result = this.isBillingSupported(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_getSkuDetails:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    android.os.Bundle _arg3;
                    if ((0!=data.readInt())) {
                        _arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg3 = null;
                    }
                    android.os.Bundle _result = this.getSkuDetails(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getBuyIntent:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    java.lang.String _arg4;
                    _arg4 = data.readString();
                    android.os.Bundle _result = this.getBuyIntent(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getPurchases:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    android.os.Bundle _result = this.getPurchases(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_consumePurchase:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _result = this.consumePurchase(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_stub:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    int _result = this.stub(_arg0, _arg1, _arg2);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                case TRANSACTION_getBuyIntentToReplaceSkus:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.util.List<java.lang.String> _arg2;
                    _arg2 = data.createStringArrayList();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    java.lang.String _arg4;
                    _arg4 = data.readString();
                    java.lang.String _arg5;
                    _arg5 = data.readString();
                    android.os.Bundle _result = this.getBuyIntentToReplaceSkus(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getBuyIntentExtraParams:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    java.lang.String _arg4;
                    _arg4 = data.readString();
                    android.os.Bundle _arg5;
                    if ((0!=data.readInt())) {
                        _arg5 = android.os.Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg5 = null;
                    }
                    android.os.Bundle _result = this.getBuyIntentExtraParams(_arg0, _arg1, _arg2, _arg3, _arg4, _arg5);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_getPurchaseHistory:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    java.lang.String _arg3;
                    _arg3 = data.readString();
                    android.os.Bundle _arg4;
                    if ((0!=data.readInt())) {
                        _arg4 = android.os.Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg4 = null;
                    }
                    android.os.Bundle _result = this.getPurchaseHistory(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    if ((_result!=null)) {
                        reply.writeInt(1);
                        _result.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
                    }
                    else {
                        reply.writeInt(0);
                    }
                    return true;
                }
                case TRANSACTION_isBillingSupportedExtraParams:
                {
                    data.enforceInterface(descriptor);
                    int _arg0;
                    _arg0 = data.readInt();
                    java.lang.String _arg1;
                    _arg1 = data.readString();
                    java.lang.String _arg2;
                    _arg2 = data.readString();
                    android.os.Bundle _arg3;
                    if ((0!=data.readInt())) {
                        _arg3 = android.os.Bundle.CREATOR.createFromParcel(data);
                    }
                    else {
                        _arg3 = null;
                    }
                    int _result = this.isBillingSupportedExtraParams(_arg0, _arg1, _arg2, _arg3);
                    reply.writeNoException();
                    reply.writeInt(_result);
                    return true;
                }
                default:
                {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }
        private static class Proxy implements com.android.vending.billing.IInAppBillingService
        {
            private android.os.IBinder mRemote;
            Proxy(android.os.IBinder remote)
            {
                mRemote = remote;
            }
            @Override public android.os.IBinder asBinder()
            {
                return mRemote;
            }
            public java.lang.String getInterfaceDescriptor()
            {
                return DESCRIPTOR;
            }

            @Override public int isBillingSupported(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_isBillingSupported, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().isBillingSupported(apiVersion, packageName, type);
                    }
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getSkuDetails(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle skusBundle) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    if ((skusBundle!=null)) {
                        _data.writeInt(1);
                        skusBundle.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getSkuDetails, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getSkuDetails(apiVersion, packageName, type, skusBundle);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getBuyIntent(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(sku);
                    _data.writeString(type);
                    _data.writeString(developerPayload);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getBuyIntent, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getBuyIntent(apiVersion, packageName, sku, type, developerPayload);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getPurchases(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    _data.writeString(continuationToken);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getPurchases, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getPurchases(apiVersion, packageName, type, continuationToken);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public int consumePurchase(int apiVersion, java.lang.String packageName, java.lang.String purchaseToken) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(purchaseToken);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_consumePurchase, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().consumePurchase(apiVersion, packageName, purchaseToken);
                    }
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public int stub(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_stub, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().stub(apiVersion, packageName, type);
                    }
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getBuyIntentToReplaceSkus(int apiVersion, java.lang.String packageName, java.util.List<java.lang.String> oldSkus, java.lang.String newSku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeStringList(oldSkus);
                    _data.writeString(newSku);
                    _data.writeString(type);
                    _data.writeString(developerPayload);
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getBuyIntentToReplaceSkus, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getBuyIntentToReplaceSkus(apiVersion, packageName, oldSkus, newSku, type, developerPayload);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getBuyIntentExtraParams(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload, android.os.Bundle extraParams) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(sku);
                    _data.writeString(type);
                    _data.writeString(developerPayload);
                    if ((extraParams!=null)) {
                        _data.writeInt(1);
                        extraParams.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getBuyIntentExtraParams, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getBuyIntentExtraParams(apiVersion, packageName, sku, type, developerPayload, extraParams);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public android.os.Bundle getPurchaseHistory(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken, android.os.Bundle extraParams) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                android.os.Bundle _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    _data.writeString(continuationToken);
                    if ((extraParams!=null)) {
                        _data.writeInt(1);
                        extraParams.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_getPurchaseHistory, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().getPurchaseHistory(apiVersion, packageName, type, continuationToken, extraParams);
                    }
                    _reply.readException();
                    if ((0!=_reply.readInt())) {
                        _result = android.os.Bundle.CREATOR.createFromParcel(_reply);
                    }
                    else {
                        _result = null;
                    }
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }

            @Override public int isBillingSupportedExtraParams(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle extraParams) throws android.os.RemoteException
            {
                android.os.Parcel _data = android.os.Parcel.obtain();
                android.os.Parcel _reply = android.os.Parcel.obtain();
                int _result;
                try {
                    _data.writeInterfaceToken(DESCRIPTOR);
                    _data.writeInt(apiVersion);
                    _data.writeString(packageName);
                    _data.writeString(type);
                    if ((extraParams!=null)) {
                        _data.writeInt(1);
                        extraParams.writeToParcel(_data, 0);
                    }
                    else {
                        _data.writeInt(0);
                    }
                    boolean _status = mRemote.transact(Stub.TRANSACTION_isBillingSupportedExtraParams, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().isBillingSupportedExtraParams(apiVersion, packageName, type, extraParams);
                    }
                    _reply.readException();
                    _result = _reply.readInt();
                }
                finally {
                    _reply.recycle();
                    _data.recycle();
                }
                return _result;
            }
            public static com.android.vending.billing.IInAppBillingService sDefaultImpl;
        }
        static final int TRANSACTION_isBillingSupported = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
        static final int TRANSACTION_getSkuDetails = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
        static final int TRANSACTION_getBuyIntent = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
        static final int TRANSACTION_getPurchases = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
        static final int TRANSACTION_consumePurchase = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
        static final int TRANSACTION_stub = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
        static final int TRANSACTION_getBuyIntentToReplaceSkus = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
        static final int TRANSACTION_getBuyIntentExtraParams = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
        static final int TRANSACTION_getPurchaseHistory = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
        static final int TRANSACTION_isBillingSupportedExtraParams = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
        public static boolean setDefaultImpl(com.android.vending.billing.IInAppBillingService impl) {
            if (Stub.Proxy.sDefaultImpl == null && impl != null) {
                Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }
        public static com.android.vending.billing.IInAppBillingService getDefaultImpl() {
            return Stub.Proxy.sDefaultImpl;
        }
    }

    public int isBillingSupported(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException;

    public android.os.Bundle getSkuDetails(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle skusBundle) throws android.os.RemoteException;

    public android.os.Bundle getBuyIntent(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException;

    public android.os.Bundle getPurchases(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken) throws android.os.RemoteException;

    public int consumePurchase(int apiVersion, java.lang.String packageName, java.lang.String purchaseToken) throws android.os.RemoteException;

    public int stub(int apiVersion, java.lang.String packageName, java.lang.String type) throws android.os.RemoteException;

    public android.os.Bundle getBuyIntentToReplaceSkus(int apiVersion, java.lang.String packageName, java.util.List<java.lang.String> oldSkus, java.lang.String newSku, java.lang.String type, java.lang.String developerPayload) throws android.os.RemoteException;

    public android.os.Bundle getBuyIntentExtraParams(int apiVersion, java.lang.String packageName, java.lang.String sku, java.lang.String type, java.lang.String developerPayload, android.os.Bundle extraParams) throws android.os.RemoteException;

    public android.os.Bundle getPurchaseHistory(int apiVersion, java.lang.String packageName, java.lang.String type, java.lang.String continuationToken, android.os.Bundle extraParams) throws android.os.RemoteException;

    public int isBillingSupportedExtraParams(int apiVersion, java.lang.String packageName, java.lang.String type, android.os.Bundle extraParams) throws android.os.RemoteException;
}