package com.anjlab.android.iab.v3;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Locale;

import static com.anjlab.android.iab.v3.data.Constants.LOCALE_US;

public class TransactionDetails implements Parcelable
{
	@Deprecated
	public final String productId;

	@Deprecated
	public final String orderId;

	@Deprecated
	public final String purchaseToken;

	@Deprecated
	public final Date purchaseTime;

	public final PurchaseInfo purchaseInfo;

	public TransactionDetails(PurchaseInfo info)
	{
		purchaseInfo = info;
		productId = purchaseInfo.purchaseData.productId;
		orderId = purchaseInfo.purchaseData.orderId;
		purchaseToken = purchaseInfo.purchaseData.purchaseToken;
		purchaseTime = purchaseInfo.purchaseData.purchaseTime;
	}

	@Override
	public String toString()
	{
		return String.format(Locale.US, LOCALE_US,
							 productId,
							 purchaseTime,
							 orderId,
							 purchaseToken,
							 purchaseInfo.signature);
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
		{
			return true;
		}
		if (o == null || getClass() != o.getClass())
		{
			return false;
		}

		TransactionDetails details = (TransactionDetails) o;

		return !(orderId != null ? !orderId.equals(details.orderId) : details.orderId != null);
	}

	@Override
	public int hashCode()
	{
		return orderId != null ? orderId.hashCode() : 0;
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeParcelable(purchaseInfo, flags);
	}

	protected TransactionDetails(Parcel in)
	{
		purchaseInfo = in.readParcelable(PurchaseInfo.class.getClassLoader());
		productId = purchaseInfo.purchaseData.productId;
		orderId = purchaseInfo.purchaseData.orderId;
		purchaseToken = purchaseInfo.purchaseData.purchaseToken;
		purchaseTime = purchaseInfo.purchaseData.purchaseTime;
	}

	public static final Parcelable.Creator<TransactionDetails> CREATOR =
			new Parcelable.Creator<TransactionDetails>()
			{
				public TransactionDetails createFromParcel(Parcel source)
				{
					return new TransactionDetails(source);
				}

				public TransactionDetails[] newArray(int size)
				{
					return new TransactionDetails[size];
				}
			};
}
