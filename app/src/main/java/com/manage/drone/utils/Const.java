package com.manage.drone.utils;


import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolygonOptions;
import com.manage.drone.R;
import com.manage.drone.models.ChildModel;
import com.manage.drone.models.GroupModel;
import com.manage.drone.models.GuideModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Phí Văn Tuấn on 24/10/2018.
 */

public class Const {
    private static List<GroupModel> groupModels;
    public static List<LatLng> lstLatLng = new ArrayList<>();

    public static String PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "tuan";

    static {
        lstLatLng.add(new LatLng(55.40514994239178, 89.45893842726947));
        lstLatLng.add(new LatLng(55.40514994239178, 89.45893842726947));
        lstLatLng.add(new LatLng(55.40512957390452, 89.45902962237595));
        lstLatLng.add(new LatLng(55.405058379296506, 89.45924755185843));
        lstLatLng.add(new LatLng(55.40492588845459, 89.45956304669379));
        lstLatLng.add(new LatLng(55.40477950078659, 89.45992950350046));
        lstLatLng.add(new LatLng(55.40463349330059, 89.46029562503101));
        lstLatLng.add(new LatLng(55.40447606350334, 89.46067951619625));
        lstLatLng.add(new LatLng(55.40425314792934, 89.46113985031842));
        lstLatLng.add(new LatLng(55.40400281852288, 89.461603872478));
        lstLatLng.add(new LatLng(55.40382311339328, 89.4619508832693));
        lstLatLng.add(new LatLng(55.403613900565745, 89.46231532841921));
        lstLatLng.add(new LatLng(55.403458941488154, 89.46256276220082));
        lstLatLng.add(new LatLng(55.403332917809976, 89.462771974504));
        lstLatLng.add(new LatLng(55.40322859579049, 89.46294095367195));
        lstLatLng.add(new LatLng(55.403128080883505, 89.46309048682451));
        lstLatLng.add(new LatLng(55.4030633552399, 89.46319811046124));
        lstLatLng.add(new LatLng(55.403017285746635, 89.46326281875373));
        lstLatLng.add(new LatLng(55.40298454210721, 89.46331713348627));
        lstLatLng.add(new LatLng(55.4029658858354, 89.46334965527058));
        lstLatLng.add(new LatLng(55.4029653147249, 89.46336071938276));
        lstLatLng.add(new LatLng(55.40295579621535, 89.46338251233101));
        lstLatLng.add(new LatLng(55.4029510369597, 89.46339324116707));
        lstLatLng.add(new LatLng(55.402941328076444, 89.46341503411531));
        lstLatLng.add(new LatLng(55.402940947335885, 89.4634260982275));
        lstLatLng.add(new LatLng(55.40293561696753, 89.46344789117575));
        lstLatLng.add(new LatLng(55.40293047696877, 89.46347001940013));
        lstLatLng.add(new LatLng(55.40292990585777, 89.46348074823618));
        lstLatLng.add(new LatLng(55.40292552733973, 89.46348074823618));
        lstLatLng.add(new LatLng(55.40292552733973, 89.46348074823618));
        lstLatLng.add(new LatLng(55.40292114882122, 89.46348074823618));
        lstLatLng.add(new LatLng(55.40291677030222, 89.46348074823618));
        lstLatLng.add(new LatLng(55.40291677030222, 89.46348074823618));

    }

    private static String getRaw(Context context) {
        try {
            InputStream openRawResource = context.getResources().openRawResource(R.raw.a);
            Reader inputStreamReader = new InputStreamReader(openRawResource, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder sb = new StringBuilder();
            while (true) {
                String readLine = bufferedReader.readLine();
                if (readLine != null) {
                    sb.append(readLine);
                } else {
                    openRawResource.close();
                    inputStreamReader.close();
                    bufferedReader.close();
                    return sb.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static final HashSet<List<LatLng>> getLatLng(Context context) {
        HashSet<List<LatLng>> hashMap = new HashSet<>();
        try {
            JSONArray array = new JSONArray(getRaw(context));
            for (int i = 0; i < array.length(); i++) {
                JSONArray array1 = array.getJSONArray(i);
                List<LatLng> lstLatLng = new ArrayList<>();
                for (int j = 0; j < array1.length(); j++) {
                    JSONObject object = array1.getJSONObject(j);
                    double lat = object.getDouble("lat");
                    double lng = object.getDouble("lng");
                    lstLatLng.add(new LatLng(lat, lng));
                }
                hashMap.add(lstLatLng);
            }
            return hashMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return hashMap;



}


    public static List<PolygonOptions> lstPolygonOptions = new ArrayList<>();

    public static HashMap<GroupModel, List<ChildModel>> getMenu(Context context) {
        HashMap<GroupModel, List<ChildModel>> hashMap = new HashMap<>();
        List<GroupModel> lstGroups = getMenuGroup(context);

        List<ChildModel> childModels = new ArrayList<>();
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_1_1)));
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_1_2)));
        hashMap.put(lstGroups.get(1), childModels);

        childModels = new ArrayList<>();
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_6_1)));
        childModels.add(new ChildModel(context.getResources().getString(R.string.group_6_2)));
        hashMap.put(lstGroups.get(6), childModels);

        return hashMap;
    }

    public static List<GroupModel> getMenuGroup(Context context) {
        if (groupModels == null) {
            groupModels = new ArrayList<>();
            groupModels.add(new GroupModel("Điều khiển drone", R.drawable.ic_control));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_1), R.drawable.icon));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_2), R.drawable.ic_zoning));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_3), R.drawable.ic_observe));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_4), R.drawable.ic_mail));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_5), R.drawable.ic_setting));
            groupModels.add(new GroupModel(context.getResources().getString(R.string.group_6), R.drawable.ic_help));
        }

        return groupModels;
    }

    public static List<GuideModel> getGuide(Context context) {
        List<GuideModel> lst = new ArrayList<>();
        lst.add(new GuideModel(context.getResources().getString(R.string.group_1_1), R.drawable.ic_connect_guide, R.color.google_red));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_1_2), R.drawable.ic_operation_guide, R.color.google_blue));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_2), R.drawable.ic_zone_guide, R.color.google_yellow));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_3), R.drawable.ic_obsever_guide, R.color.google_green));
        lst.add(new GuideModel(context.getResources().getString(R.string.group_4), R.drawable.ic_message_guide, R.color.colorSwitch));
        lst.add(new GuideModel("Điều khiển drone", R.drawable.ic_control_guide, R.color.google_red));

        return lst;
    }

}
