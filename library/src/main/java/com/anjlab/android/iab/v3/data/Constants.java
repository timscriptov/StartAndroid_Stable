package com.anjlab.android.iab.v3.data;

import com.anjlab.android.iab.v3.utils.Utils;

public final class Constants {
	
	public static final String COM_ANDROID_VENDING_BILLING_IINAPPBILLINGSERVICE = Utils.fromBase64("Y29tLmFuZHJvaWQudmVuZGluZy5iaWxsaW5nLklJbkFwcEJpbGxpbmdTZXJ2aWNl"); // com.android.vending.billing.IInAppBillingService
	
	public static final String LOCALE_US = Utils.fromBase64("JXMgcHVyY2hhc2VkIGF0ICVzKCVzKS4gVG9rZW46ICVzLCBTaWduYXR1cmU6ICVz"); // "%s purchased at %s(%s). Token: %s, Signature: %s"
	public static final String LOCALE_US_2 = Utils.fromBase64("JXM6ICVzKCVzKSAlZiBpbiAlcyAoJXMp"); // "%s: %s(%s) %f in %s (%s)"
	public static final String PREFERENCES = Utils.fromBase64("X3ByZWZlcmVuY2Vz"); // _preferences
	public static final String VERSION = Utils.fromBase64("LnZlcnNpb24="); // .version
	public static final String IABV3_PURCHASEINFO = Utils.fromBase64("aWFidjMucHVyY2hhc2VJbmZv"); // iabv3.purchaseInfo
	public static final String IABUTIL_SECURITY = Utils.fromBase64("SUFCVXRpbC9TZWN1cml0eQ=="); // IABUtil/Security
	public static final String RSA = Utils.fromBase64("UlNB"); // RSA
	public static final String SHA1WITHRSA = Utils.fromBase64("U0hBMXdpdGhSU0E="); // SHA1withRSA
	public static final String ANDROID_TEST_PURCHASED = Utils.fromBase64("YW5kcm9pZC50ZXN0LnB1cmNoYXNlZA=="); // android.test.purchased
	public static final String ANDROID_TEST_CANCELED = Utils.fromBase64("YW5kcm9pZC50ZXN0LmNhbmNlbGVk"); // android.test.canceled
	public static final String ANDROID_TEST_REFUNDED = Utils.fromBase64("YW5kcm9pZC50ZXN0LnJlZnVuZGVk"); // android.test.refunded
	public static final String ANDROID_TEST_ITEM_UNAVAILABLE = Utils.fromBase64("YW5kcm9pZC50ZXN0Lml0ZW1fdW5hdmFpbGFibGU="); // android.test.item_unavailable
	public static final String COM_ANDROID_VENDING_BILLING_INAPPBILLINGSERVICE_BIND = Utils.fromBase64("Y29tLmFuZHJvaWQudmVuZGluZy5iaWxsaW5nLkluQXBwQmlsbGluZ1NlcnZpY2UuQklORA=="); // com.android.vending.billing.InAppBillingService.BIND
	public static final String COM_ANDROID_VENDING = Utils.fromBase64("Y29tLmFuZHJvaWQudmVuZGluZw=="); // com.android.vending
	public static final String IABV3 = Utils.fromBase64("aWFidjM="); // iabv3
	
	/*************************/
	public static final int GOOGLE_API_VERSION = 3;
	public static final int GOOGLE_API_SUBSCRIPTION_CHANGE_VERSION = 5;
	public static final int GOOGLE_API_VR_SUPPORTED_VERSION = 7;

	public static final String PRODUCT_TYPE_MANAGED = Utils.fromBase64("aW5hcHA="); // inapp
	public static final String PRODUCT_TYPE_SUBSCRIPTION = Utils.fromBase64("c3Vicw=="); // subs

	//Success
	public static final int BILLING_RESPONSE_RESULT_OK = 0;

	//User pressed back or canceled a dialog
	public static final int BILLING_RESPONSE_RESULT_USER_CANCELED = 1;

	// Network connection is down
	public static final int BILLING_RESPONSE_RESULT_SERVICE_UNAVAILABLE = 2;

	//Billing API version is not supported for the type requested
	public static final int BILLING_RESPONSE_RESULT_BILLING_UNAVAILABLE = 3;

	//Requested product is not available for purchase
	public static final int BILLING_RESPONSE_RESULT_ITEM_UNAVAILABLE = 4;

	//Invalid arguments provided to the API. This error can also indicate that the application
	// was not correctly signed or properly set up for In-app Billing in Google Play, or
	// does not have the necessary permissions in its manifest
	public static final int BILLING_RESPONSE_RESULT_DEVELOPER_ERROR = 5;

	//Fatal error during the API action
	public static final int BILLING_RESPONSE_RESULT_ERROR = 6;

	//Failure to purchase since item is already owned
	public static final int BILLING_RESPONSE_RESULT_ITEM_ALREADY_OWNED = 7;

	//Failure to consume since item is not owned
	public static final int BILLING_RESPONSE_RESULT_ITEM_NOT_OWNED = 8;

	public static final String RESPONSE_CODE = Utils.fromBase64("UkVTUE9OU0VfQ09ERQ=="); // RESPONSE_CODE
	public static final String DETAILS_LIST = Utils.fromBase64("REVUQUlMU19MSVNU"); // DETAILS_LIST
	public static final String PRODUCTS_LIST = Utils.fromBase64("SVRFTV9JRF9MSVNU"); // ITEM_ID_LIST
	public static final String BUY_INTENT = Utils.fromBase64("QlVZX0lOVEVOVA=="); // BUY_INTENT
	public static final String INAPP_PURCHASE_DATA_LIST = Utils.fromBase64("SU5BUFBfUFVSQ0hBU0VfREFUQV9MSVNU"); // INAPP_PURCHASE_DATA_LIST
	public static final String INAPP_PURCHASE_DATA = Utils.fromBase64("SU5BUFBfUFVSQ0hBU0VfREFUQQ=="); // INAPP_PURCHASE_DATA
	public static final String RESPONSE_INAPP_SIGNATURE = Utils.fromBase64("SU5BUFBfREFUQV9TSUdOQVRVUkU="); // INAPP_DATA_SIGNATURE
	public static final String INAPP_DATA_SIGNATURE_LIST = Utils.fromBase64("SU5BUFBfREFUQV9TSUdOQVRVUkVfTElTVA=="); // INAPP_DATA_SIGNATURE_LIST
	public static final String RESPONSE_ORDER_ID = Utils.fromBase64("b3JkZXJJZA=="); // orderId
	public static final String RESPONSE_PRODUCT_ID = Utils.fromBase64("cHJvZHVjdElk"); // productId
	public static final String RESPONSE_PACKAGE_NAME = Utils.fromBase64("cGFja2FnZU5hbWU="); // packageName
	public static final String RESPONSE_PURCHASE_TIME = Utils.fromBase64("cHVyY2hhc2VUaW1l"); // purchaseTime
	public static final String RESPONSE_PURCHASE_STATE = Utils.fromBase64("cHVyY2hhc2VTdGF0ZQ=="); // purchaseState
	public static final String RESPONSE_PURCHASE_TOKEN = Utils.fromBase64("cHVyY2hhc2VUb2tlbg=="); // purchaseToken
	public static final String RESPONSE_DEVELOPER_PAYLOAD = Utils.fromBase64("ZGV2ZWxvcGVyUGF5bG9hZA=="); // developerPayload
	public static final String RESPONSE_TYPE = Utils.fromBase64("dHlwZQ=="); // type
	public static final String RESPONSE_TITLE = Utils.fromBase64("dGl0bGU="); // title
	public static final String RESPONSE_DESCRIPTION = Utils.fromBase64("ZGVzY3JpcHRpb24="); // description
	public static final String RESPONSE_PRICE = Utils.fromBase64("cHJpY2U="); // price
	public static final String RESPONSE_PRICE_CURRENCY = Utils.fromBase64("cHJpY2VfY3VycmVuY3lfY29kZQ=="); // price_currency_code
	public static final String RESPONSE_PRICE_MICROS = Utils.fromBase64("cHJpY2VfYW1vdW50X21pY3Jvcw=="); // price_amount_micros
	public static final String RESPONSE_SUBSCRIPTION_PERIOD = Utils.fromBase64("c3Vic2NyaXB0aW9uUGVyaW9k"); // subscriptionPeriod
	public static final String RESPONSE_AUTO_RENEWING = Utils.fromBase64("YXV0b1JlbmV3aW5n"); // autoRenewing
	public static final String RESPONSE_FREE_TRIAL_PERIOD = Utils.fromBase64("ZnJlZVRyaWFsUGVyaW9k"); // freeTrialPeriod
	public static final String RESPONSE_INTRODUCTORY_PRICE = Utils.fromBase64("aW50cm9kdWN0b3J5UHJpY2U="); // introductoryPrice
	public static final String RESPONSE_INTRODUCTORY_PRICE_MICROS = Utils.fromBase64("aW50cm9kdWN0b3J5UHJpY2VBbW91bnRNaWNyb3M="); // introductoryPriceAmountMicros
	public static final String RESPONSE_INTRODUCTORY_PRICE_PERIOD = Utils.fromBase64("aW50cm9kdWN0b3J5UHJpY2VQZXJpb2Q="); // introductoryPricePeriod
	public static final String RESPONSE_INTRODUCTORY_PRICE_CYCLES = Utils.fromBase64("aW50cm9kdWN0b3J5UHJpY2VDeWNsZXM="); // introductoryPriceCycles

	public static final int BILLING_ERROR_FAILED_LOAD_PURCHASES = 100;
	public static final int BILLING_ERROR_FAILED_TO_INITIALIZE_PURCHASE = 101;
	public static final int BILLING_ERROR_INVALID_SIGNATURE = 102;
	public static final int BILLING_ERROR_LOST_CONTEXT = 103;
	public static final int BILLING_ERROR_INVALID_MERCHANT_ID = 104;
	@Deprecated
	public static final int BILLING_ERROR_INVALID_DEVELOPER_PAYLOAD = 105;
	public static final int BILLING_ERROR_OTHER_ERROR = 110;
	public static final int BILLING_ERROR_CONSUME_FAILED = 111;
	public static final int BILLING_ERROR_SKUDETAILS_FAILED = 112;
	public static final int BILLING_ERROR_BIND_PLAY_STORE_FAILED = 113;

	public static final String EXTRA_PARAMS_KEY_VR = Utils.fromBase64("dnI="); // vr
	public static final String EXTRA_PARAMS_KEY_SKU_TO_REPLACE = Utils.fromBase64("c2t1c1RvUmVwbGFjZQ=="); // skusToReplace
	
	
}
