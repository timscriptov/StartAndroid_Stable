package com.anjlab.android.iab.v3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class PurchaseData implements Parcelable
{
    public String orderId;
    public String packageName;
    public String productId;
    public Date purchaseTime;
    public PurchaseState purchaseState;
    public String developerPayload;
    public String purchaseToken;
    public boolean autoRenewing;

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(orderId);
        dest.writeString(packageName);
        dest.writeString(productId);
        dest.writeLong(purchaseTime != null ? purchaseTime.getTime() : -1);
        dest.writeInt(purchaseState == null ? -1 : purchaseState.ordinal());
        dest.writeString(developerPayload);
        dest.writeString(purchaseToken);
        dest.writeByte(autoRenewing ? (byte) 1 : (byte) 0);
    }

    public PurchaseData()
    {
    }

    protected PurchaseData(Parcel in)
    {
        orderId = in.readString();
        packageName = in.readString();
        productId = in.readString();
        long tmpPurchaseTime = in.readLong();
        purchaseTime = tmpPurchaseTime == -1 ? null : new Date(tmpPurchaseTime);
        int tmpPurchaseState = in.readInt();
        purchaseState =
                tmpPurchaseState == -1 ? null : PurchaseState.values()[tmpPurchaseState];
        developerPayload = in.readString();
        purchaseToken = in.readString();
        autoRenewing = in.readByte() != 0;
    }

    public static final Parcelable.Creator<PurchaseData> CREATOR =
            new Parcelable.Creator<PurchaseData>()
            {
                public PurchaseData createFromParcel(Parcel source)
                {
                    return new PurchaseData(source);
                }

                public PurchaseData[] newArray(int size)
                {
                    return new PurchaseData[size];
                }
            };
}
