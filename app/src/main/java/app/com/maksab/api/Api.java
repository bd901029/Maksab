package app.com.maksab.api;

import app.com.maksab.api.dao.*;
import app.com.maksab.view.viewmodel.*;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    // http://www.maksabapp.com/develop_api
    String BASE_URL = "https://www.maksabapp.com/develop_api/";

    String BASE_URL2 = "https://srstaging.stspayone.com/SmartRoutePaymentWeb/";

    String SUCCESS = "200";

    @POST("api/set_api_token")
    Call<SuccessfulResponse> setApiToken(@Body TokenModel tokenModel);

    @POST("api/signup")
    Call<RegistrationResponse> registerUser(@Body UserRegistrationModel userRegistrationModel);

    @POST("api/login")
    Call<LoginResponse> loginUser(@Body LoginModel loginModel);

    @POST("api/forgot_password")
    Call<SuccessfulResponse> forgotPassword(@Body ForgotModel forgotModel);

    @POST("api/get_country_city")
    Call<CountryCityListResponse> getCountryCity(@Body LanguageModel languageModel);

    @POST("api/get_category_list")
    Call<CategoryHomeResponse> getCategoryList(@Body LanguageModel languageModel);

    @POST("api/getCity")
    Call<CityListResponse> getCity(@Body CityModel cityModel);

    @POST("api/get_home_data")
    Call<HomeDataResponse> getHomeData(@Body HomeDataModel homeDataModel);

    @POST("api/get_near_by_brand")
    Call<NearByResponse> getNearByBrand(@Body LatLngModel latLngModel);

    @POST("api/get_home_setting")
    Call<SuccessfulResponse> getHomeSetting(@Body LanguageModel languageModel);

    @POST("api/get_all_brand")
    Call<FavoritePartnerListResponse> getAllBrand(@Body UserCityIdModel userCityIdModel);

    @POST("api/get_brand_details")
    Call<BrandDetailsResponse> getBrandDetails(@Body UserCityPartnerModel userCityPartnerModel);

    @POST("api/add_partner_in_wishlist")
    Call<AddFavoritesResponse> addPartnerInWishlist(@Body UserPartnerIdModel userPartnerIdModel);

    @POST("api/get_offer_details")
    Call<OfferDetailsResponse> getOfferDetails(@Body UserCityOfferModel userCityOfferModel);

    @POST("api/add_offer_in_wishlist")
    Call<AddFavoritesResponse> addOfferInWishlist(@Body UserOfferIdModel userOfferIdModel);

    @POST("api/get_filter_category")
    Call<OfferFilterResponse> getFilterCategory(@Body CityCatIdModel cityCatIdModel);

    @POST("api/add_offer_in_wishlist")
    Call<SuccessfulResponse> addOfferInWishlist(@Body LanguageModel storeModel);

    @POST("api/offer_list")
    Call<OfferListResponse> offerList(@Body OfferListModel offerListModel);

    @POST("api/get_upcoming_purchase")
    Call<SuccessfulResponse> getUpcomingPurchase(@Body UserIdModel userIdModel);

    @POST("api/get_past_purchase")
    Call<SuccessfulResponse> getPastPurchase(@Body UserIdModel userIdModel);

    @POST("api/get_my_order_amount")
    Call<GetOrderAmount>  getOrderAmount(@Body UserIdModel userIdModel);

    @POST("api/get_upcoming_purchase")
    Call<UpcomingPurchaseModel> getUpcomingPurchase(@Body UserCityIdModel userIdModel);

    @POST("api/delete_upcoming_purchase")
    Call<SuccessfulResponse> deleteUpcomingPurchase(@Body OrderIdModel orderIdModel);

    @POST("api/get_past_purchase")
    Call<UpcomingPurchaseModel> getPastPurchase(@Body UserCityIdModel userIdModel);

    @POST("api/get_point_reward")
    Call<PointsRewardModel> getPointReward(@Body UserIdModel userIdModel);

    @POST("api/get_user_subscription")
    Call<SubscriptionModel> getUserSubscription(@Body UserIdModel userIdModel);

    @POST("api/get_gift_history")
    Call<GiftHistoryModel> getGiftistory(@Body UserIdModel userIdModel);

    @POST("api/get_gift_receive")
    Call<SendGiftsResponce> getGiftReceive(@Body UserIdModel userIdModel);

    @POST("api/reedeem_offer")
    Call<RedeemResponse> redeemOffer(@Body RedeemModel redeemModel);

    @POST("api/send_gift")
    Call<SuccessfulResponse> sendGift(@Body GiftModel giftModel);

    @POST("api/get_gift_details")
    Call<GetGiftResponse> getGiftDetails(@Body UserCityIdModel userCityIdModel);

    @POST("api/get_user_category")
    Call<NotificationFilterResponse> getUserCategory(@Body UserIdModel userIdModel);

    @POST("api/select_category_notification")
    Call<SuccessfulResponse> selectCategoryNotification(@Body SetNotificationFilterModel setNotificationFilterModel);

    @POST("api/get_notification")
    Call<NotificationResponse> getNotification(@Body UserCityIdModel userCityIdModel);

    @POST("api/get_user_notification_setting")
    Call<NotificationStatusResponse> getUserNotificationSetting(@Body UserIdModel userIdModel);

    @POST("api/change_user_notification_setting")
    Call<SuccessfulResponse> changeUserNotificationSetting(@Body UserIdNotificationModel userIdNotificationModel);

    @POST("api/get_profile")
    Call<ProfileResponse> getProfile(@Body UserIdModel userIdModel);

    @POST("api/edit_profile")
    Call<SuccessfulResponse> editProfile(@Body UpdateProfileDataModel updateProfileDataModel);

    @POST("api/send_mobile_verification_code")
    Call<SuccessfulResponse> sendMobileVerification(@Body UserIdMobileModel userIdMobileModel);

    @POST("api/check_verification_code")
    Call<SuccessfulResponse> checkVerificationCode(@Body UserIdOTPModel userIdOTPModel);

    @POST("api/favorite_offer_list")
    Call<FavoriteOfferListResponse> favoriteOfferList(@Body UserCityIdModel userCityIdModel);

    @POST("api/favorite_partner_list")
    Call<FavoritePartnerListResponse> favoritePartnerList(@Body UserCityIdModel userCityIdModel);

    @POST("api/change_password")
    Call<SuccessfulResponse> changePassword(@Body ChangePasswordModel changePasswordModel);

    @POST("api/get_contact")
    Call<ContactUsResponse> getContact(@Body CountryIdModel countryIdModel);

    @POST("api/save_contact")
    Call<SuccessfulResponse> saveContact(@Body ContactUsModel contactUsModel);

    @POST("api/get_faq")
    Call<FaqModel> getFaq(@Body LanguageModel languageModel);

    @POST("api/become_partner")
    Call<SuccessfulResponse> becomePartner(@Body BecomePartnerModel becomePartnerModel);

    @POST("api/get_plan_list")
    Call<PackagesResponse> getPlanList(@Body UserCityIdModel userCityIdModel);

    @POST("api/check_coupon_code")
    Call<CouponCodeResponse> checkCouponCode(@Body CouponCodeModel couponCodeModel);

    @POST("api/payment_complete")
    Call<SuccessfulResponse> paymentComplete(@Body PaymentCompleteModel paymentCompleteModel);

    @POST("api/become_partner")
    Call<SuccessfulResponse> becomePartner(@Body LanguageModel homeDataModel);

    @POST("api/get_invitation_link")
    Call<SuccessfulResponse> getInvitationLink(@Body UserIdModel userIdModel);

    @POST("api/send_invitation")
    Call<SuccessfulResponse> sendInvitation(@Body LanguageModel homeDataModel);

    @POST("api/set_review_rate")
    Call<SuccessfulResponse> setReviewRate(@Body RateReviewModel rateReviewModel);

    @POST("api/get_search_result")
    Call<SearchListResponse> getSearchResult(@Body SearchModel searchModel);

    @POST("api/get_terms_and_condition")
    Call<TcResponse> getTermsAndCondition();

    @POST("api/get_privacy_policy")
    Call<PpResponse> getPrivacyPolicy();

    @POST("api/get_privacy_policy")
    Call<SearchListResponse> getPrivacyPolicy(@Body SearchModel searchModel);

    @POST("api/get_family_member")
    Call<GetFamilyMembers> getFamilyMember(@Body UserIdModel model);

    @POST("api/add_family_member")
    Call<SuccessfulResponse> addFamilyMember(@Body FamilyModel model);

    @POST("api/delete_family_member")
    Call<SuccessfulResponse> deleteFamily_member(@Body MemberIdModel memberIdModel);

    @POST("api/check_otp")
    Call<OTPRegisterResponse> checkOtp(@Body MobileModel mobileModel);

    @POST("api/get_mobile_number")
    Call<MobileResponse> getMobileNumber(@Body EmailModel emailModel);

    @POST("api/reSendOTP")
    Call<SuccessfulResponse> reSendOTP(@Body ResendOTPModel resendOTPModel);

    @POST("api/logout")
    Call<SuccessfulResponse> logout(@Body LogoutModel logoutModel);

    @POST("api/free_plan")
    Call<SuccessfulResponse> freePlan(@Body FreePlanModel freePlanModel);

    @Multipart
    @POST("ApiWithFile/edit_profile_pic")
    Call<LoginResponse> editProfilePic(@Part MultipartBody.Part user_img,
                                       @Part("language") RequestBody language,
                                       @Part("user_id") RequestBody userId);

    @Multipart
    @POST("ApiWithFile/careerRequest")
    Call<SuccessfulResponse> careerRequest(@Part MultipartBody.Part user_img,
                                       @Part("language") RequestBody language,
                                      @Part("fullname") RequestBody fullname,
                                      @Part("nationality") RequestBody nationality,
                                      @Part("dob") RequestBody dob,
                                      @Part("job_title") RequestBody job_title,
                                      @Part("email") RequestBody email,
                                      @Part("phone") RequestBody phone);

    /*
    language, fullname, nationality, dob, job_title, email, phone, user_img

    @Multipart
    @POST("ApiWithFile/careerRequest")
    Call<SuccessfulResponse> careerRequest(@Part MultipartBody.Part user_img,
                                        @Part("language") RequestBody language,
                                        @Part("fullname") RequestBody fullname,
                                        @Part("nationality") RequestBody nationality,
                                        @Part("dob") RequestBody dob,
                                        @Part("job_title") RequestBody jobTitle,
                                        @Part("email") RequestBody email,
                                        @Part("phone") RequestBody phone);*/
   /* @Multipart
    @POST("ApiWithFile/careerRequest")
    Call<SuccessfulResponse> careerRequest(
            @Part("language") RequestBody language,
            @Part("fullname") RequestBody fullname,
            @Part("nationality") RequestBody nationality,
            @Part("dob") RequestBody dob,
            @Part("job_title") RequestBody jobTitle,
            @Part("email") RequestBody email,
            @Part("phone") RequestBody phone,
            @Part MultipartBody.Part user_img);*/

}
