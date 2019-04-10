package com.gene.library.api;

import java.util.List;

import com.gene.library.pojo.Asset;
import com.gene.library.pojo.Balance;
import com.gene.library.pojo.Coin;
import com.gene.library.pojo.Income;
import com.gene.library.pojo.Login;
import com.gene.library.pojo.Market;
import com.gene.library.pojo.Register;
import com.gene.library.pojo.Trade;
import com.gene.library.pojo.UserInfo;
import com.gene.library.pojo.Wallet;
import com.gene.library.pojo.RxResult;

import okhttp3.MultipartBody;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by MaoLJ on 2018/7/18.
 * 接口服务类
 */

public interface ApiService {

    // 发送验证码
    @GET("d-app/API/getAuthCode")
    Observable<RxResult<String>> getAuthCode(@Query("mobile") String mobile, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 校验验证码
    @GET("d-app/API/checkAuthCode")
    Observable<RxResult<String>> checkAuthCode(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("mobile") String mobile, @Query("verifyCode") String verifyCode);

    // 注册
    @GET("d-app/API/register")
    Observable<RxResult<Register>> register(@Query("bindMobile") String bindMobile, @Query("passWord") String passWord, @Query("payPassWord") String payPassWord, @Query("sign") String sign, @Query("submitTime") String submitTime, @Query("nickName") String nickName, @Query("verifyCode") String verifyCode);

    // 登陆
    @GET("d-app/API/userLogin")
    Observable<RxResult<Login>> userLogin(@Query("loginAccount") String loginAccount, @Query("verifyCode") String verifyCode, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 个人钱包列表+个人总资产
    @GET("d-app/coins/memUserWallet/findList.json")
    Observable<RxResult<Wallet>> findList(@Query("appSessionId") String appSessionId, @Query("sign") String sign, @Query("submitTime") String submitTime);

    // 可添加钱包列表
    @GET("d-app/coins/memUserWallet/findAllWalletList.json")
    Observable<RxResult<List<Coin>>> findAllWalletList(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId);

    // 添加钱包
    @GET("d-app/coins/memUserWallet/getMemUserWallet.do")
    Observable<RxResult<Asset>> addWallet(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType);

    // 查询用户对应钱包金额
    @GET("d-app/coins/income/getIncome.json")
    Observable<RxResult<Income>> getIncome(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType);

    // 查询交易明细
    @GET("d-app/coins/income/findBalanceList.json")
    Observable<RxResult<List<Balance>>> findBalanceList(@Query("sign") String sign, @Query("submitTime") String submitTime, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType, @Query("count") int count);

    // 转币
    @GET("d-app/coins/Ore/transferToOthers.do")
    Observable<RxResult<Object>> transferToOthers(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId, @Query("userWalletType") String userWalletType, @Query("outAddress") String outAddress, @Query("transferAmount") String transferAmount, @Query("payPassword") String payPassword, @Query("feeType") int feeType);

    // 个人信息
    @GET("d-app/API/getUserInfo")
    Observable<RxResult<UserInfo>> getUserInfo(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 行情
    @GET("d-app/coins/getTradeInfo.json")
    Observable<RxResult<List<Trade>>> getTradeInfo(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 昵称头像修改
    @GET("d-app/API/resetNickNameAndImg")
    Observable<RxResult<Object>> resetNickNameAndImg(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId, @Query("nickName") String nickName, @Query("imageUrl") String imageUrl);

    // 资讯
    @GET("notice/findnewsList.json")
    Observable<RxResult<Market>> findNewList(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 上传registerId
    @GET("d-app/API/savaAudieanceId")
    Observable<RxResult<Object>> registerId(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId, @Query("audienceId") String audienceId);

    // 修改支付密码
    @GET("d-app/API/updateUserPwd")
    Observable<RxResult<Object>> updateUserPwd(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId, @Query("oldPassword") String oldPassword, @Query("password") String password, @Query("pwdType") int pwdType);

    // 设置是否显示钱包
    @GET("d-app/coins/memUserWallet/resetWalletShowStatus.do")
    Observable<RxResult<Object>> setWalletShow(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId, @Query("isShow") boolean isShow, @Query("coinType") String coinType);

    // 全部已读
    @GET("notice/markAllRead.do")
    Observable<RxResult<Object>> markAllRead(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 汇率
    @GET("d-app/coins/getUsdtToRmb.json")
    Observable<RxResult<Double>> usdToCny(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 上传用户头像
    @Multipart
    @POST("d-app/API/uploadImg")
    Observable<RxResult<String>> uploadAvatar(@Part MultipartBody.Part file, @Query("appSessionId") String appSessionId);

    // 退出登录
    @GET("d-app/API/logout")
    Observable<RxResult<Object>> logout(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("appSessionId") String appSessionId);

    // 判断手机号是否已注册
    @GET("d-app/API/getUserByLoginAccount")
    Observable<RxResult<Object>> isRegister(@Query("submitTime") String submitTime, @Query("sign") String sign, @Query("loginAccount") String loginAccount);

}


