package com.anjlab.android.iab.v3;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.anjlab.android.iab.v3.data.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import static com.anjlab.android.iab.v3.data.Constants.LOCALE_US_2;

public class SkuDetails implements Parcelable
{

    public final String productId;

    public final String title;

    public final String description;

    public final boolean isSubscription;

    public final String currency;

    public final Double priceValue;

    public final String subscriptionPeriod;

    public final String subscriptionFreeTrialPeriod;

    public final boolean haveTrialPeriod;

    public final double introductoryPriceValue;

    public final String introductoryPricePeriod;

    public final boolean haveIntroductoryPeriod;

    public final int introductoryPriceCycles;

    public final long priceLong;

    public final String priceText;

    public final long introductoryPriceLong;

    public final String introductoryPriceText;

    public SkuDetails(JSONObject source) throws JSONException
    {
        String responseType = source.optString(Constants.RESPONSE_TYPE);
        if (responseType == null)
        {
            responseType = Constants.PRODUCT_TYPE_MANAGED;
        }
        productId = source.optString(Constants.RESPONSE_PRODUCT_ID);
        title = source.optString(Constants.RESPONSE_TITLE);
        description = source.optString(Constants.RESPONSE_DESCRIPTION);
        isSubscription = responseType.equalsIgnoreCase(Constants.PRODUCT_TYPE_SUBSCRIPTION);
        currency = source.optString(Constants.RESPONSE_PRICE_CURRENCY);
        priceLong = source.optLong(Constants.RESPONSE_PRICE_MICROS);
        priceValue = priceLong / 1000000d;
        priceText = source.optString(Constants.RESPONSE_PRICE);
        subscriptionPeriod = source.optString(Constants.RESPONSE_SUBSCRIPTION_PERIOD);
        subscriptionFreeTrialPeriod = source.optString(Constants.RESPONSE_FREE_TRIAL_PERIOD);
        haveTrialPeriod = !TextUtils.isEmpty(subscriptionFreeTrialPeriod);
        introductoryPriceLong = source.optLong(Constants.RESPONSE_INTRODUCTORY_PRICE_MICROS);
        introductoryPriceValue = introductoryPriceLong / 1000000d;
        introductoryPriceText = source.optString(Constants.RESPONSE_INTRODUCTORY_PRICE);
        introductoryPricePeriod = source.optString(Constants.RESPONSE_INTRODUCTORY_PRICE_PERIOD);
        haveIntroductoryPeriod = !TextUtils.isEmpty(introductoryPricePeriod);
        introductoryPriceCycles = source.optInt(Constants.RESPONSE_INTRODUCTORY_PRICE_CYCLES);
    }

    @Override
    public String toString()
    {
        return String.format(Locale.US, LOCALE_US_2,
                productId,
                title,
                description,
                priceValue,
                currency,
                priceText);
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

        SkuDetails that = (SkuDetails) o;

        if (isSubscription != that.isSubscription)
        {
            return false;
        }
        return !(productId != null ? !productId.equals(that.productId) : that.productId != null);
    }

    @Override
    public int hashCode()
    {
        int result = productId != null ? productId.hashCode() : 0;
        result = 31 * result + (isSubscription ? 1 : 0);
        return result;
    }

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeString(productId);
        dest.writeString(title);
        dest.writeString(description);
        dest.writeByte(isSubscription ? (byte) 1 : (byte) 0);
        dest.writeString(currency);
        dest.writeDouble(priceValue);
        dest.writeLong(priceLong);
        dest.writeString(priceText);
        dest.writeString(subscriptionPeriod);
        dest.writeString(subscriptionFreeTrialPeriod);
        dest.writeByte(haveTrialPeriod ? (byte) 1 : (byte) 0);
        dest.writeDouble(introductoryPriceValue);
        dest.writeLong(introductoryPriceLong);
        dest.writeString(introductoryPriceText);
        dest.writeString(introductoryPricePeriod);
        dest.writeByte(haveIntroductoryPeriod ? (byte) 1 : (byte) 0);
        dest.writeInt(introductoryPriceCycles);
    }

    protected SkuDetails(Parcel in)
    {
        productId = in.readString();
        title = in.readString();
        description = in.readString();
        isSubscription = in.readByte() != 0;
        currency = in.readString();
        priceValue = in.readDouble();
        priceLong = in.readLong();
        priceText = in.readString();
        subscriptionPeriod = in.readString();
        subscriptionFreeTrialPeriod = in.readString();
        haveTrialPeriod = in.readByte() != 0;
        introductoryPriceValue = in.readDouble();
        introductoryPriceLong = in.readLong();
        introductoryPriceText = in.readString();
        introductoryPricePeriod = in.readString();
        haveIntroductoryPeriod = in.readByte() != 0;
        introductoryPriceCycles = in.readInt();
    }

    public static final Parcelable.Creator<SkuDetails> CREATOR =
            new Parcelable.Creator<SkuDetails>()
            {
                public SkuDetails createFromParcel(Parcel source)
                {
                    return new SkuDetails(source);
                }

                public SkuDetails[] newArray(int size)
                {
                    return new SkuDetails[size];
                }
            };
}
