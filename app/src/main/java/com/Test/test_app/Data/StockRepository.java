package com.Test.test_app.Data;

import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.Test.test_app.Api.ApiHolder;
import com.Test.test_app.Api.pojoModels.CompanyProfile;
import com.Test.test_app.Api.pojoModels.ConstituentModel;
import com.Test.test_app.Api.pojoModels.QuoteModel;
import com.Test.test_app.Api.pojoModels.SearchList;
import com.Test.test_app.Api.pojoModels.SearchResultList;
import com.Test.test_app.App;
import com.Test.test_app.R;
import com.Test.test_app.Stock;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StockRepository {

    //-----------------------------Working with data-----------------------------------------//

    private StockDao stockDao;
    private LiveData<List<Stock>> favoriteList;
    private JSONObject jsonObject;

    public StockRepository() {
        stockDao = App.getInstance().getStockDao();
        favoriteList = stockDao.getFavoriteList();
        searchList.setValue(new ArrayList<Stock>());
        defaultList.setValue(new ArrayList<Stock>());
        defaultProcessCode.setValue(200);
        searchProcessCode.setValue(200);
        String currencies = "{\"AED\":{\"symbol\":\"AED\",\"code\":\"AED\",\"symbol_native\":\"د.إ.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"AFN\":{\"symbol\":\"AFN\",\"code\":\"AFN\",\"symbol_native\":\"؋\",\"decimal_digits\":0,\"rounding\":0.0},\"ALL\":{\"symbol\":\"ALL\",\"code\":\"ALL\",\"symbol_native\":\"Lekë\",\"decimal_digits\":0,\"rounding\":0.0},\"AMD\":{\"symbol\":\"AMD\",\"code\":\"AMD\",\"symbol_native\":\"֏\",\"decimal_digits\":0,\"rounding\":0.0},\"ANG\":{\"symbol\":\"ANG\",\"code\":\"ANG\",\"symbol_native\":\"NAf.\",\"decimal_digits\":2,\"rounding\":0.0},\"AOA\":{\"symbol\":\"AOA\",\"code\":\"AOA\",\"symbol_native\":\"Kz\",\"decimal_digits\":2,\"rounding\":0.0},\"ARS\":{\"symbol\":\"ARS\",\"code\":\"ARS\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"AUD\":{\"symbol\":\"A$\",\"code\":\"AUD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"AWG\":{\"symbol\":\"AWG\",\"code\":\"AWG\",\"symbol_native\":\"Afl.\",\"decimal_digits\":2,\"rounding\":0.0},\"AZN\":{\"symbol\":\"AZN\",\"code\":\"AZN\",\"symbol_native\":\"\\u20BC\",\"decimal_digits\":2,\"rounding\":0.0},\"BAM\":{\"symbol\":\"BAM\",\"code\":\"BAM\",\"symbol_native\":\"КМ\",\"decimal_digits\":2,\"rounding\":0.0},\"BBD\":{\"symbol\":\"BBD\",\"code\":\"BBD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"BDT\":{\"symbol\":\"BDT\",\"code\":\"BDT\",\"symbol_native\":\"৳\",\"decimal_digits\":2,\"rounding\":0.0},\"BGN\":{\"symbol\":\"BGN\",\"code\":\"BGN\",\"symbol_native\":\"лв.\",\"decimal_digits\":2,\"rounding\":0.0},\"BHD\":{\"symbol\":\"BHD\",\"code\":\"BHD\",\"symbol_native\":\"د.ب.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"BIF\":{\"symbol\":\"BIF\",\"code\":\"BIF\",\"symbol_native\":\"FBu\",\"decimal_digits\":0,\"rounding\":0.0},\"BMD\":{\"symbol\":\"BMD\",\"code\":\"BMD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"BND\":{\"symbol\":\"BND\",\"code\":\"BND\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"BOB\":{\"symbol\":\"BOB\",\"code\":\"BOB\",\"symbol_native\":\"Bs\",\"decimal_digits\":2,\"rounding\":0.0},\"BOV\":{\"symbol\":\"BOV\",\"code\":\"BOV\",\"symbol_native\":\"BOV\",\"decimal_digits\":2,\"rounding\":0.0},\"BRL\":{\"symbol\":\"R$\",\"code\":\"BRL\",\"symbol_native\":\"R$\",\"decimal_digits\":2,\"rounding\":0.0},\"BSD\":{\"symbol\":\"BSD\",\"code\":\"BSD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"BTN\":{\"symbol\":\"BTN\",\"code\":\"BTN\",\"symbol_native\":\"Nu.\",\"decimal_digits\":2,\"rounding\":0.0},\"BWP\":{\"symbol\":\"BWP\",\"code\":\"BWP\",\"symbol_native\":\"P\",\"decimal_digits\":2,\"rounding\":0.0},\"BYN\":{\"symbol\":\"BYN\",\"code\":\"BYN\",\"symbol_native\":\"Br\",\"decimal_digits\":2,\"rounding\":0.0},\"BZD\":{\"symbol\":\"BZD\",\"code\":\"BZD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"CAD\":{\"symbol\":\"CA$\",\"code\":\"CAD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"CDF\":{\"symbol\":\"CDF\",\"code\":\"CDF\",\"symbol_native\":\"FC\",\"decimal_digits\":2,\"rounding\":0.0},\"CHE\":{\"symbol\":\"CHE\",\"code\":\"CHE\",\"symbol_native\":\"CHE\",\"decimal_digits\":2,\"rounding\":0.0},\"CHF\":{\"symbol\":\"CHF\",\"code\":\"CHF\",\"symbol_native\":\"CHF\",\"decimal_digits\":2,\"rounding\":0.0},\"CHW\":{\"symbol\":\"CHW\",\"code\":\"CHW\",\"symbol_native\":\"CHW\",\"decimal_digits\":2,\"rounding\":0.0},\"CLF\":{\"symbol\":\"CLF\",\"code\":\"CLF\",\"symbol_native\":\"CLF\",\"decimal_digits\":4,\"rounding\":0.0},\"CLP\":{\"symbol\":\"CLP\",\"code\":\"CLP\",\"symbol_native\":\"$\",\"decimal_digits\":0,\"rounding\":0.0},\"CNH\":{\"symbol\":\"CNH\",\"code\":\"CNH\",\"symbol_native\":\"CNH\",\"decimal_digits\":2,\"rounding\":0.0},\"CNY\":{\"symbol\":\"CN¥\",\"code\":\"CNY\",\"symbol_native\":\"¥\",\"decimal_digits\":2,\"rounding\":0.0},\"COP\":{\"symbol\":\"COP\",\"code\":\"COP\",\"symbol_native\":\"$\",\"decimal_digits\":0,\"rounding\":0.0},\"COU\":{\"symbol\":\"COU\",\"code\":\"COU\",\"symbol_native\":\"COU\",\"decimal_digits\":2,\"rounding\":0.0},\"CRC\":{\"symbol\":\"CRC\",\"code\":\"CRC\",\"symbol_native\":\"\\u20A1\",\"decimal_digits\":2,\"rounding\":0.0},\"CUC\":{\"symbol\":\"CUC\",\"code\":\"CUC\",\"symbol_native\":\"CUC\",\"decimal_digits\":2,\"rounding\":0.0},\"CUP\":{\"symbol\":\"CUP\",\"code\":\"CUP\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"CVE\":{\"symbol\":\"CVE\",\"code\":\"CVE\",\"symbol_native\":\"\\u200B\",\"decimal_digits\":2,\"rounding\":0.0},\"CZK\":{\"symbol\":\"CZK\",\"code\":\"CZK\",\"symbol_native\":\"Kč\",\"decimal_digits\":2,\"rounding\":0.0},\"DJF\":{\"symbol\":\"DJF\",\"code\":\"DJF\",\"symbol_native\":\"Fdj\",\"decimal_digits\":0,\"rounding\":0.0},\"DKK\":{\"symbol\":\"DKK\",\"code\":\"DKK\",\"symbol_native\":\"kr.\",\"decimal_digits\":2,\"rounding\":0.0},\"DOP\":{\"symbol\":\"DOP\",\"code\":\"DOP\",\"symbol_native\":\"RD$\",\"decimal_digits\":2,\"rounding\":0.0},\"DZD\":{\"symbol\":\"DZD\",\"code\":\"DZD\",\"symbol_native\":\"د.ج.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"EGP\":{\"symbol\":\"EGP\",\"code\":\"EGP\",\"symbol_native\":\"ج.م.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"ERN\":{\"symbol\":\"ERN\",\"code\":\"ERN\",\"symbol_native\":\"Nfk\",\"decimal_digits\":2,\"rounding\":0.0},\"ETB\":{\"symbol\":\"ETB\",\"code\":\"ETB\",\"symbol_native\":\"ብር\",\"decimal_digits\":2,\"rounding\":0.0},\"EUR\":{\"symbol\":\"\\u20AC\",\"code\":\"EUR\",\"symbol_native\":\"\\u20AC\",\"decimal_digits\":2,\"rounding\":0.0},\"FJD\":{\"symbol\":\"FJD\",\"code\":\"FJD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"FKP\":{\"symbol\":\"FKP\",\"code\":\"FKP\",\"symbol_native\":\"£\",\"decimal_digits\":2,\"rounding\":0.0},\"GBP\":{\"symbol\":\"£\",\"code\":\"GBP\",\"symbol_native\":\"£\",\"decimal_digits\":2,\"rounding\":0.0},\"GEL\":{\"symbol\":\"GEL\",\"code\":\"GEL\",\"symbol_native\":\"\\u20BE\",\"decimal_digits\":2,\"rounding\":0.0},\"GHS\":{\"symbol\":\"GHS\",\"code\":\"GHS\",\"symbol_native\":\"GH\\u20B5\",\"decimal_digits\":2,\"rounding\":0.0},\"GIP\":{\"symbol\":\"GIP\",\"code\":\"GIP\",\"symbol_native\":\"£\",\"decimal_digits\":2,\"rounding\":0.0},\"GMD\":{\"symbol\":\"GMD\",\"code\":\"GMD\",\"symbol_native\":\"D\",\"decimal_digits\":2,\"rounding\":0.0},\"GNF\":{\"symbol\":\"GNF\",\"code\":\"GNF\",\"symbol_native\":\"FG\",\"decimal_digits\":0,\"rounding\":0.0},\"GTQ\":{\"symbol\":\"GTQ\",\"code\":\"GTQ\",\"symbol_native\":\"Q\",\"decimal_digits\":2,\"rounding\":0.0},\"GYD\":{\"symbol\":\"GYD\",\"code\":\"GYD\",\"symbol_native\":\"$\",\"decimal_digits\":0,\"rounding\":0.0},\"HKD\":{\"symbol\":\"HK$\",\"code\":\"HKD\",\"symbol_native\":\"HK$\",\"decimal_digits\":2,\"rounding\":0.0},\"HNL\":{\"symbol\":\"HNL\",\"code\":\"HNL\",\"symbol_native\":\"L\",\"decimal_digits\":2,\"rounding\":0.0},\"HRK\":{\"symbol\":\"HRK\",\"code\":\"HRK\",\"symbol_native\":\"HRK\",\"decimal_digits\":2,\"rounding\":0.0},\"HTG\":{\"symbol\":\"HTG\",\"code\":\"HTG\",\"symbol_native\":\"G\",\"decimal_digits\":2,\"rounding\":0.0},\"HUF\":{\"symbol\":\"HUF\",\"code\":\"HUF\",\"symbol_native\":\"Ft\",\"decimal_digits\":2,\"rounding\":0.0},\"IDR\":{\"symbol\":\"IDR\",\"code\":\"IDR\",\"symbol_native\":\"Rp\",\"decimal_digits\":0,\"rounding\":0.0},\"ILS\":{\"symbol\":\"\\u20AA\",\"code\":\"ILS\",\"symbol_native\":\"\\u20AA\",\"decimal_digits\":2,\"rounding\":0.0},\"INR\":{\"symbol\":\"\\u20B9\",\"code\":\"INR\",\"symbol_native\":\"\\u20B9\",\"decimal_digits\":2,\"rounding\":0.0},\"IQD\":{\"symbol\":\"IQD\",\"code\":\"IQD\",\"symbol_native\":\"د.ع.\\u200F\",\"decimal_digits\":0,\"rounding\":0.0},\"IRR\":{\"symbol\":\"IRR\",\"code\":\"IRR\",\"symbol_native\":\"IRR\",\"decimal_digits\":0,\"rounding\":0.0},\"ISK\":{\"symbol\":\"ISK\",\"code\":\"ISK\",\"symbol_native\":\"ISK\",\"decimal_digits\":0,\"rounding\":0.0},\"JMD\":{\"symbol\":\"JMD\",\"code\":\"JMD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"JOD\":{\"symbol\":\"JOD\",\"code\":\"JOD\",\"symbol_native\":\"د.أ.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"JPY\":{\"symbol\":\"¥\",\"code\":\"JPY\",\"symbol_native\":\"￥\",\"decimal_digits\":0,\"rounding\":0.0},\"KES\":{\"symbol\":\"KES\",\"code\":\"KES\",\"symbol_native\":\"Ksh\",\"decimal_digits\":2,\"rounding\":0.0},\"KGS\":{\"symbol\":\"KGS\",\"code\":\"KGS\",\"symbol_native\":\"сом\",\"decimal_digits\":2,\"rounding\":0.0},\"KHR\":{\"symbol\":\"KHR\",\"code\":\"KHR\",\"symbol_native\":\"៛\",\"decimal_digits\":2,\"rounding\":0.0},\"KMF\":{\"symbol\":\"KMF\",\"code\":\"KMF\",\"symbol_native\":\"CF\",\"decimal_digits\":0,\"rounding\":0.0},\"KPW\":{\"symbol\":\"KPW\",\"code\":\"KPW\",\"symbol_native\":\"KPW\",\"decimal_digits\":0,\"rounding\":0.0},\"KRW\":{\"symbol\":\"\\u20A9\",\"code\":\"KRW\",\"symbol_native\":\"\\u20A9\",\"decimal_digits\":0,\"rounding\":0.0},\"KWD\":{\"symbol\":\"KWD\",\"code\":\"KWD\",\"symbol_native\":\"د.ك.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"KYD\":{\"symbol\":\"KYD\",\"code\":\"KYD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"KZT\":{\"symbol\":\"KZT\",\"code\":\"KZT\",\"symbol_native\":\"\\u20B8\",\"decimal_digits\":2,\"rounding\":0.0},\"LAK\":{\"symbol\":\"LAK\",\"code\":\"LAK\",\"symbol_native\":\"\\u20AD\",\"decimal_digits\":0,\"rounding\":0.0},\"LBP\":{\"symbol\":\"LBP\",\"code\":\"LBP\",\"symbol_native\":\"ل.ل.\\u200F\",\"decimal_digits\":0,\"rounding\":0.0},\"LKR\":{\"symbol\":\"LKR\",\"code\":\"LKR\",\"symbol_native\":\"රු.\",\"decimal_digits\":2,\"rounding\":0.0},\"LRD\":{\"symbol\":\"LRD\",\"code\":\"LRD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"LSL\":{\"symbol\":\"LSL\",\"code\":\"LSL\",\"symbol_native\":\"LSL\",\"decimal_digits\":2,\"rounding\":0.0},\"LYD\":{\"symbol\":\"LYD\",\"code\":\"LYD\",\"symbol_native\":\"د.ل.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"MAD\":{\"symbol\":\"MAD\",\"code\":\"MAD\",\"symbol_native\":\"د.م.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"MDL\":{\"symbol\":\"MDL\",\"code\":\"MDL\",\"symbol_native\":\"L\",\"decimal_digits\":2,\"rounding\":0.0},\"MGA\":{\"symbol\":\"MGA\",\"code\":\"MGA\",\"symbol_native\":\"Ar\",\"decimal_digits\":0,\"rounding\":0.0},\"MKD\":{\"symbol\":\"MKD\",\"code\":\"MKD\",\"symbol_native\":\"ден\",\"decimal_digits\":2,\"rounding\":0.0},\"MMK\":{\"symbol\":\"MMK\",\"code\":\"MMK\",\"symbol_native\":\"K\",\"decimal_digits\":0,\"rounding\":0.0},\"MNT\":{\"symbol\":\"MNT\",\"code\":\"MNT\",\"symbol_native\":\"\\u20AE\",\"decimal_digits\":0,\"rounding\":0.0},\"MOP\":{\"symbol\":\"MOP\",\"code\":\"MOP\",\"symbol_native\":\"MOP$\",\"decimal_digits\":2,\"rounding\":0.0},\"MRO\":{\"symbol\":\"MRO\",\"code\":\"MRO\",\"symbol_native\":\"أ.م.\\u200F\",\"decimal_digits\":0,\"rounding\":0.0},\"MUR\":{\"symbol\":\"MUR\",\"code\":\"MUR\",\"symbol_native\":\"Rs\",\"decimal_digits\":0,\"rounding\":0.0},\"MWK\":{\"symbol\":\"MWK\",\"code\":\"MWK\",\"symbol_native\":\"MK\",\"decimal_digits\":2,\"rounding\":0.0},\"MXN\":{\"symbol\":\"MX$\",\"code\":\"MXN\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"MXV\":{\"symbol\":\"MXV\",\"code\":\"MXV\",\"symbol_native\":\"MXV\",\"decimal_digits\":2,\"rounding\":0.0},\"MYR\":{\"symbol\":\"MYR\",\"code\":\"MYR\",\"symbol_native\":\"RM\",\"decimal_digits\":2,\"rounding\":0.0},\"MZN\":{\"symbol\":\"MZN\",\"code\":\"MZN\",\"symbol_native\":\"MTn\",\"decimal_digits\":2,\"rounding\":0.0},\"NAD\":{\"symbol\":\"NAD\",\"code\":\"NAD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"NGN\":{\"symbol\":\"NGN\",\"code\":\"NGN\",\"symbol_native\":\"\\u20A6\",\"decimal_digits\":2,\"rounding\":0.0},\"NIO\":{\"symbol\":\"NIO\",\"code\":\"NIO\",\"symbol_native\":\"C$\",\"decimal_digits\":2,\"rounding\":0.0},\"NOK\":{\"symbol\":\"NOK\",\"code\":\"NOK\",\"symbol_native\":\"kr\",\"decimal_digits\":2,\"rounding\":0.0},\"NPR\":{\"symbol\":\"NPR\",\"code\":\"NPR\",\"symbol_native\":\"नेरू\",\"decimal_digits\":2,\"rounding\":0.0},\"NZD\":{\"symbol\":\"NZ$\",\"code\":\"NZD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"OMR\":{\"symbol\":\"OMR\",\"code\":\"OMR\",\"symbol_native\":\"ر.ع.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"PAB\":{\"symbol\":\"PAB\",\"code\":\"PAB\",\"symbol_native\":\"B\\/.\",\"decimal_digits\":2,\"rounding\":0.0},\"PEN\":{\"symbol\":\"PEN\",\"code\":\"PEN\",\"symbol_native\":\"S\\/\",\"decimal_digits\":2,\"rounding\":0.0},\"PGK\":{\"symbol\":\"PGK\",\"code\":\"PGK\",\"symbol_native\":\"K\",\"decimal_digits\":2,\"rounding\":0.0},\"PHP\":{\"symbol\":\"PHP\",\"code\":\"PHP\",\"symbol_native\":\"\\u20B1\",\"decimal_digits\":2,\"rounding\":0.0},\"PKR\":{\"symbol\":\"PKR\",\"code\":\"PKR\",\"symbol_native\":\"Rs\",\"decimal_digits\":0,\"rounding\":0.0},\"PLN\":{\"symbol\":\"PLN\",\"code\":\"PLN\",\"symbol_native\":\"zł\",\"decimal_digits\":2,\"rounding\":0.0},\"PYG\":{\"symbol\":\"PYG\",\"code\":\"PYG\",\"symbol_native\":\"Gs.\",\"decimal_digits\":0,\"rounding\":0.0},\"QAR\":{\"symbol\":\"QAR\",\"code\":\"QAR\",\"symbol_native\":\"ر.ق.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"RON\":{\"symbol\":\"RON\",\"code\":\"RON\",\"symbol_native\":\"RON\",\"decimal_digits\":2,\"rounding\":0.0},\"RSD\":{\"symbol\":\"RSD\",\"code\":\"RSD\",\"symbol_native\":\"RSD\",\"decimal_digits\":0,\"rounding\":0.0},\"RUB\":{\"symbol\":\"\\u20BD\",\"code\":\"RUB\",\"symbol_native\":\"\\u20BD\",\"decimal_digits\":2,\"rounding\":0.0},\"RWF\":{\"symbol\":\"RWF\",\"code\":\"RWF\",\"symbol_native\":\"RF\",\"decimal_digits\":0,\"rounding\":0.0},\"SAR\":{\"symbol\":\"SAR\",\"code\":\"SAR\",\"symbol_native\":\"ر.س.\\u200F\",\"decimal_digits\":2,\"rounding\":0.0},\"SBD\":{\"symbol\":\"SBD\",\"code\":\"SBD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"SCR\":{\"symbol\":\"SCR\",\"code\":\"SCR\",\"symbol_native\":\"SR\",\"decimal_digits\":2,\"rounding\":0.0},\"SDG\":{\"symbol\":\"SDG\",\"code\":\"SDG\",\"symbol_native\":\"ج.س.\",\"decimal_digits\":2,\"rounding\":0.0},\"SEK\":{\"symbol\":\"SEK\",\"code\":\"SEK\",\"symbol_native\":\"kr\",\"decimal_digits\":2,\"rounding\":0.0},\"SGD\":{\"symbol\":\"SGD\",\"code\":\"SGD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"SHP\":{\"symbol\":\"SHP\",\"code\":\"SHP\",\"symbol_native\":\"£\",\"decimal_digits\":2,\"rounding\":0.0},\"SLL\":{\"symbol\":\"SLL\",\"code\":\"SLL\",\"symbol_native\":\"Le\",\"decimal_digits\":0,\"rounding\":0.0},\"SOS\":{\"symbol\":\"SOS\",\"code\":\"SOS\",\"symbol_native\":\"S\",\"decimal_digits\":0,\"rounding\":0.0},\"SRD\":{\"symbol\":\"SRD\",\"code\":\"SRD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"SSP\":{\"symbol\":\"SSP\",\"code\":\"SSP\",\"symbol_native\":\"£\",\"decimal_digits\":2,\"rounding\":0.0},\"STN\":{\"symbol\":\"STN\",\"code\":\"STN\",\"symbol_native\":\"STN\",\"decimal_digits\":2,\"rounding\":0.0},\"SYP\":{\"symbol\":\"SYP\",\"code\":\"SYP\",\"symbol_native\":\"ل.س.\\u200F\",\"decimal_digits\":0,\"rounding\":0.0},\"SZL\":{\"symbol\":\"SZL\",\"code\":\"SZL\",\"symbol_native\":\"E\",\"decimal_digits\":2,\"rounding\":0.0},\"THB\":{\"symbol\":\"฿\",\"code\":\"THB\",\"symbol_native\":\"THB\",\"decimal_digits\":2,\"rounding\":0.0},\"TJS\":{\"symbol\":\"TJS\",\"code\":\"TJS\",\"symbol_native\":\"сом.\",\"decimal_digits\":2,\"rounding\":0.0},\"TND\":{\"symbol\":\"TND\",\"code\":\"TND\",\"symbol_native\":\"د.ت.\\u200F\",\"decimal_digits\":3,\"rounding\":0.0},\"TOP\":{\"symbol\":\"TOP\",\"code\":\"TOP\",\"symbol_native\":\"T$\",\"decimal_digits\":2,\"rounding\":0.0},\"TRY\":{\"symbol\":\"TRY\",\"code\":\"TRY\",\"symbol_native\":\"\\u20BA\",\"decimal_digits\":2,\"rounding\":0.0},\"TTD\":{\"symbol\":\"TTD\",\"code\":\"TTD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"TWD\":{\"symbol\":\"NT$\",\"code\":\"TWD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"TZS\":{\"symbol\":\"TZS\",\"code\":\"TZS\",\"symbol_native\":\"TSh\",\"decimal_digits\":0,\"rounding\":0.0},\"UAH\":{\"symbol\":\"\\u20B4\",\"code\":\"UAH\",\"symbol_native\":\"\\u20B4\",\"decimal_digits\":2,\"rounding\":0.0},\"UGX\":{\"symbol\":\"UGX\",\"code\":\"UGX\",\"symbol_native\":\"USh\",\"decimal_digits\":0,\"rounding\":0.0},\"USD\":{\"symbol\":\"$\",\"code\":\"USD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"USN\":{\"symbol\":\"USN\",\"code\":\"USN\",\"symbol_native\":\"USN\",\"decimal_digits\":2,\"rounding\":0.0},\"UYI\":{\"symbol\":\"UYI\",\"code\":\"UYI\",\"symbol_native\":\"UYI\",\"decimal_digits\":0,\"rounding\":0.0},\"UYU\":{\"symbol\":\"UYU\",\"code\":\"UYU\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"UZS\":{\"symbol\":\"UZS\",\"code\":\"UZS\",\"symbol_native\":\"сўм\",\"decimal_digits\":0,\"rounding\":0.0},\"VEF\":{\"symbol\":\"VEF\",\"code\":\"VEF\",\"symbol_native\":\"Bs.\",\"decimal_digits\":2,\"rounding\":0.0},\"VND\":{\"symbol\":\"\\u20AB\",\"code\":\"VND\",\"symbol_native\":\"\\u20AB\",\"decimal_digits\":0,\"rounding\":0.0},\"VUV\":{\"symbol\":\"VUV\",\"code\":\"VUV\",\"symbol_native\":\"VT\",\"decimal_digits\":0,\"rounding\":0.0},\"WST\":{\"symbol\":\"WST\",\"code\":\"WST\",\"symbol_native\":\"WS$\",\"decimal_digits\":2,\"rounding\":0.0},\"XAF\":{\"symbol\":\"FCFA\",\"code\":\"XAF\",\"symbol_native\":\"FCFA\",\"decimal_digits\":0,\"rounding\":0.0},\"XCD\":{\"symbol\":\"EC$\",\"code\":\"XCD\",\"symbol_native\":\"$\",\"decimal_digits\":2,\"rounding\":0.0},\"XOF\":{\"symbol\":\"CFA\",\"code\":\"XOF\",\"symbol_native\":\"CFA\",\"decimal_digits\":0,\"rounding\":0.0},\"XPF\":{\"symbol\":\"CFPF\",\"code\":\"XPF\",\"symbol_native\":\"FCFP\",\"decimal_digits\":0,\"rounding\":0.0},\"YER\":{\"symbol\":\"YER\",\"code\":\"YER\",\"symbol_native\":\"ر.ي.\\u200F\",\"decimal_digits\":0,\"rounding\":0.0},\"ZAR\":{\"symbol\":\"ZAR\",\"code\":\"ZAR\",\"symbol_native\":\"R\",\"decimal_digits\":2,\"rounding\":0.0},\"ZMW\":{\"symbol\":\"ZMW\",\"code\":\"ZMW\",\"symbol_native\":\"K\",\"decimal_digits\":2,\"rounding\":0.0}}";
        try {
            jsonObject = new JSONObject(currencies);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void insert(Stock stock) {
        Log.i("TAG", "Try to insert a new stock in the thread");
        new Thread(new Runnable() {
            @Override
            public void run() {
                stockDao.insert(stock);
            }
        }).start();
    }

    public void update(Stock stock) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stockDao.update(stock);
            }
        }).start();
    }

    public void delete(Stock stock) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stockDao.delete(stock);
            }
        }).start();
    }

    public LiveData<List<Stock>> getFavoriteList() {
        return favoriteList;
    }

    //-----------------------------Working with the net-----------------------------------------//

    private MutableLiveData<List<Stock>> searchList = new MutableLiveData<List<Stock>>();
    private MutableLiveData<List<Stock>> defaultList = new MutableLiveData<List<Stock>>();

    private MutableLiveData<Integer> defaultProcessCode = new MutableLiveData<>();
    private MutableLiveData<Integer> searchProcessCode = new MutableLiveData<>();
    private Thread searchThread = null;

    private ApiHolder apiHolder = App.getInstance().getApiHolder();
    private String TAG = "TAG";

    public MutableLiveData<List<Stock>> getDefaultList() {
        return defaultList;
    }

    public MutableLiveData<List<Stock>> getSearchList() {
        return searchList;
    }

    public MutableLiveData<Integer> getDefaultProcessCode() {
        return defaultProcessCode;
    }

    public MutableLiveData<Integer> getSearchProcessCode() {
        return searchProcessCode;
    }

    public void getStocks(String index) {
        Call<ConstituentModel> call = apiHolder.getConstituents(index);
        call.enqueue(new Callback<ConstituentModel>() {
            @Override
            public void onResponse(Call<ConstituentModel> call, Response<ConstituentModel> response) {
                if (!response.isSuccessful()) {
                    defaultProcessCode.setValue(response.code());
                    return;
                }
                List<String> constituents = response.body().getConstituents();
                for (int i = 0; i < Math.min(constituents.size(), 15); i++) {
                    getProfile(constituents.get(i), defaultList, defaultProcessCode, i + 1, 15);
                }
            }

            @Override
            public void onFailure(Call<ConstituentModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    public void SearchQuery(String query) {
        searchThread = new Thread(() -> {
            Call<SearchList> call = apiHolder.getSearchList(query);
            try {
                Response<SearchList> response = call.execute();
                if (!response.isSuccessful()) {
                    searchProcessCode.postValue(response.code());
                    return;
                }
                if (response.body().getCount() == 0) {
                    searchProcessCode.postValue(0);
                    return;
                }
                List<SearchResultList> result = response.body().getResult();
                for (int i = 0; i < response.body().getCount(); i++) {
                    SearchResultList res = result.get(i);
                    if (res.getType().equals("Common Stock")) {
                        Log.i(TAG, "Trying to get Profile of " + res.getSymbol());
                        getProfile(res.getSymbol(), searchList, searchProcessCode, i + 1, response.body().getCount());
                        Thread.sleep(300);
                    } else if (i == response.body().getCount() - 1) {
                        searchProcessCode.postValue(999);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        });
        searchThread.start();
    }

    public void InterruptSearch() {
        if (searchThread != null && searchThread.isAlive()) {
            Log.i("TAG", "Search Thread interrupted");
            searchThread.interrupt();
        }
    }

    void getProfile(String symbol, MutableLiveData<List<Stock>> LiveStockList, MutableLiveData<Integer> responseCode, int j, int count) {
        Call<CompanyProfile> call = apiHolder.getProfile(symbol);
        call.enqueue(new Callback<CompanyProfile>() {
            @Override
            public void onResponse(Call<CompanyProfile> call, Response<CompanyProfile> response) {
                if (!response.isSuccessful()) {
                    responseCode.setValue(response.code());
                    return;
                }
                if (response.body() == null || response.body().getTicker() == null || response.body().getTicker().equals("")) {
                    return;
                }
                Stock stock = new Stock();
                Log.i(TAG, "Got Profile of " + response.body().getTicker());
                CompanyProfile profile = response.body();
                stock.setTicker(profile.getTicker());
                stock.setName(profile.getName());
                stock.setLogoUrl(GetURL(profile.getLogo()));
                try {
                    stock.setCurrency(getCurrency(profile.getCurrency()));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getCurrDiff(symbol, stock, LiveStockList, responseCode, j, count);
            }

            @Override
            public void onFailure(Call<CompanyProfile> call, Throwable t) {
                Log.i(TAG, t.getMessage());
            }
        });
    }

    void getCurrDiff(String symbol, Stock stock, MutableLiveData<List<Stock>> LiveStockList, MutableLiveData<Integer> responseCode, int j, int count) {
        Call<QuoteModel> call = apiHolder.getQuote(symbol);
        Log.i(TAG, "Trying to get Quote of " + symbol);
        call.enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                if (!response.isSuccessful()) {
                    responseCode.setValue(response.code());
                    return;
                }
                if (response.body() == null) {
                    return;
                }
                Log.i(TAG, "Got Quote of " + symbol);
                stock.setCurrentPrice(response.body().getCurrent());
                stock.setDifferent(response.body().getDifferent());
                Log.i("TAG", "trying to check in favorite " + stock.getTicker());
                if (favoriteList.getValue() != null) {
                    Log.i("TAG", stock.getTicker() + "checked in favorite ");
                    stock.setStarMode(R.drawable.star_selected);
                    if (!Check(stock.getTicker(), favoriteList.getValue())) {
                        stock.setStarMode(R.drawable.star_unselected);
                    }
                } else {
                    Log.i("TAG", "Favorite was null, while " + stock.getTicker() + " is running");
                }
                LiveStockList.getValue().add(stock);
                LiveStockList.setValue(LiveStockList.getValue());
                if (j == count) {
                    responseCode.setValue(999);
                }
            }

            @Override
            public void onFailure(Call<QuoteModel> call, Throwable t) {
                Log.i(TAG, t.getMessage());
                if (j == count) {
                    responseCode.setValue(999);
                }
            }

        });
    }

    private boolean Check(String ticker, List<Stock> stockList) {
        for (Stock stock : stockList) {
            if (stock.getTicker().equals(ticker)) {
                return true;
            }
        }
        return false;
    }

    String GetURL(String logoUrl) {
        Uri.Builder builder = new Uri.Builder();
        if (logoUrl == null || logoUrl.isEmpty()) {
            return "";
        }
        String host = "";
        try {
            host = getUrlHost(logoUrl);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return "";
        }
        builder.scheme("https")
                .authority("logo.clearbit.com")
                .appendPath(host)
                .build();
        return builder.toString();
    }

    String getUrlHost(String url) throws URISyntaxException {
        URI uri = new URI(url);
        return uri.getHost();
    }

    String getCurrency(String Currency) throws JSONException {
        return jsonObject.getJSONObject(Currency).getString("symbol");
    }
}
