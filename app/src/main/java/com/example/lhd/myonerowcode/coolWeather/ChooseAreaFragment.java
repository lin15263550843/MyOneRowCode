package com.example.lhd.myonerowcode.coolWeather;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lhd.myonerowcode.R;
import com.example.lhd.myonerowcode.adapter.TestOneAdapter;
import com.example.lhd.myonerowcode.db.City;
import com.example.lhd.myonerowcode.db.County;
import com.example.lhd.myonerowcode.db.Province;
import com.example.lhd.myonerowcode.entity.TestOneItemsActivity;
import com.example.lhd.myonerowcode.util.HttpUtil;
import com.example.lhd.myonerowcode.service.MyApplication;
import com.example.lhd.myonerowcode.util.Utility;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by dhc on 2018/9/4.
 * 显示省市县数据的碎片
 */

public class ChooseAreaFragment extends Fragment {
    private static final String TAG = "ChooseAreaFragment";
    public static final int LEVEL_PROVINCE = 0;
    public static final int LEVEL_CITY = 1;
    public static final int LEVEL_COUNTY = 2;
    private ProgressDialog progressDialog;
    private TextView titleText;
    private Button back;
    private ListView listView;
    //    private ArrayAdapter<String> arrayAdapter;
    private TestOneAdapter arrayAdapter;
    //    private List<String> dataList = new ArrayList<>();
    private List<TestOneItemsActivity> testOneList = new ArrayList<>();
    // 省列表
    private List<Province> provinceList;
    // 市列表
    private List<City> cityList;
    // 县列表
    private List<County> countyList;
    // 选中的省份
    private Province selectedProvince;
    // 选中的城市
    private City selectedCity;
    // 当前选中的级别
    private int currentLevel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        return super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.choose_area_layout, container, false);
        titleText = (TextView) view.findViewById(R.id.choose_area_title_text);
        back = (Button) view.findViewById(R.id.choose_area_back);
        listView = (ListView) view.findViewById(R.id.choose_area_list);
//        arrayAdapter = new ArrayAdapter<String>(MyApplication.getContext(), android.R.layout.simple_list_item_1, dataList);
        arrayAdapter = new TestOneAdapter(MyApplication.getContext(), R.layout.test_one_items_layout, testOneList);
        listView.setAdapter(arrayAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            if (currentLevel == LEVEL_PROVINCE) {
                selectedProvince = provinceList.get(position);
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                selectedCity = cityList.get(position);
                queryCounties();
            }
        });
        // 返回按钮操作
        back.setOnClickListener(v -> {
            if (currentLevel == LEVEL_PROVINCE) {
                getActivity().finish();
            } else if (currentLevel == LEVEL_COUNTY) {
                queryCities();
            } else if (currentLevel == LEVEL_CITY) {
                queryProvinces();
            }
        });
        queryProvinces();
    }

    /**
     * 查询全国所有的省，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryProvinces() {
        titleText.setText("中国");
//        back.setVisibility(View.GONE);
        provinceList = DataSupport.findAll(Province.class);
        if (provinceList.size() > 0) {
//            dataList.clear();
            testOneList.clear();
            for (Province province : provinceList) {
//                dataList.add(province.getProvinceName());
                TestOneItemsActivity testOneItem = new TestOneItemsActivity("000000", province.getProvinceName(), R.drawable.jiaohu_on, province.getProvinceCode() + "");
                testOneList.add(testOneItem);
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_PROVINCE;
        } else {
            String address = "http://guolin.tech/api/china";
            queryFromServer(address, "province");
        }
    }

    /**
     * 查询选中省内所有的市，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCities() {
        titleText.setText(selectedProvince.getProvinceName());
        back.setVisibility(View.VISIBLE);
        List<City> testList = DataSupport.findAll(City.class);
        Log.d(TAG, "queryCities: " + testList);
        cityList = DataSupport.where("provinceId = ?", String.valueOf(selectedProvince.getId())).find(City.class);
        if (cityList.size() > 0) {
//            dataList.clear();
            testOneList.clear();
            for (City city : cityList) {
//                dataList.add(city.getCityName());
                TestOneItemsActivity testOneItem = new TestOneItemsActivity("000000", city.getCityName() + "---", R.drawable.jiaohu_on, city.getCtyCode() + "");
                testOneList.add(testOneItem);
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_CITY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            String address = "http://guolin.tech/api/china/" + provinceCode;
            queryFromServer(address, "city");
        }
    }

    /**
     * 查询选中室内所有的县，优先从数据库查询，如果没有查询到再去服务器上查询
     */
    private void queryCounties() {
        titleText.setText(selectedCity.getCityName());
        back.setVisibility(View.VISIBLE);
        countyList = DataSupport.where("cityId = ?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
//            dataList.clear();
            testOneList.clear();
            for (County county : countyList) {
//                dataList.add(county.getCountyName());
                TestOneItemsActivity testOneItem = new TestOneItemsActivity("000000", county.getCountyName(), R.drawable.jiaohu_on, county.getWeatherId() + "");
                testOneList.add(testOneItem);
            }
            arrayAdapter.notifyDataSetChanged();
            listView.setSelection(0);
            currentLevel = LEVEL_COUNTY;
        } else {
            int provinceCode = selectedProvince.getProvinceCode();
            int cityCode = selectedCity.getCtyCode();
            String address = "http://guolin.tech/api/china/" + provinceCode + "/" + cityCode;
            queryFromServer(address, "county");
        }

    }

    /**
     * 根据传入的地址和类型从服务器上查询省市县数据
     *
     * @param address
     * @param type
     */
    private void queryFromServer(String address, final String type) {
        showProgressDialog();
        HttpUtil.sendOkHttpRequest(address, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 通过 runOnUiThread() 方法回到主线程处理逻辑
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        closeProgressDialog();
                        Toast.makeText(MyApplication.getContext(), "加载失败", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                boolean result = false;
                if ("province".equals(type)) {
                    result = Utility.handleProvinceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = Utility.handleCityResponse(responseText, selectedProvince.getId());
                } else if ("county".equals(type)) {
                    result = Utility.handCountyResponse(responseText, selectedCity.getId());
                }
                if (result) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            closeProgressDialog();
                            if ("province".equals(type)) {
                                queryProvinces();
                            } else if ("city".equals(type)) {
                                queryCities();
                            } else if ("county".equals(type)) {
                                queryCounties();
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度对话框
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("正在加载中...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }
}
