package com.manage.drone.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manage.drone.AppAction;
import com.manage.drone.AppConstant;
import com.manage.drone.R;
import com.manage.drone.adapter.BaseRecycleViewAdapter;
import com.manage.drone.adapter.ConnectAdapter;
import com.manage.drone.adapter.SuggestAdapter;
import com.manage.drone.customs.VerticalSpacesItemDecoration;
import com.manage.drone.models.BaseItemModel;
import com.manage.drone.models.HeaderConnect;
import com.manage.drone.models.ItemConnect;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

/**
 * Created by Phí Văn Tuấn on 30/10/2018.
 */

public class ConnectFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener, AppConstant {
    public static ConnectFragment newInstance() {
        Bundle args = new Bundle();
        ConnectFragment fragment = new ConnectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rcvList)
    RecyclerView rcvList;
    @BindView(R.id.edtSearch)
    AutoCompleteTextView completeTextView;
    SuggestAdapter suggestAdapter;

    ConnectAdapter connectAdapter;
    List<BaseItemModel> items = new ArrayList<>();
    List<ItemConnect> itemsConnect = new ArrayList<>();

    @Override
    protected int getLayoutRes() {
        return R.layout.connect_fragment;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void initData() {
        rcvList.setLayoutManager(new LinearLayoutManager(getContext()));
        connectAdapter = new ConnectAdapter(getActivity(), this);
        rcvList.addItemDecoration(new VerticalSpacesItemDecoration(10, connectAdapter));
        rcvList.setAdapter(connectAdapter);
        dummy();
        connectAdapter.replace(items);
        suggestAdapter = new SuggestAdapter(getContext(), 0, itemsConnect);
        completeTextView.setAdapter(suggestAdapter);
        suggestAdapter.replace(itemsConnect);
        suggestAdapter.setItemClick(new SuggestAdapter.ItemClick() {
            @Override
            public void onItemClick(ItemConnect item) {
                if (item != null) {
                    if (connectAdapter.checkConnected(item.getTitle())) {
                        Toast.makeText(getContext(), "Drone đang kết nối", Toast.LENGTH_SHORT).show();
                    } else {
                        showDialog(item.getTitle());
                    }
                }
            }
        });

    }

    @Override
    public void onItemClick(int position) {
        BaseItemModel item = connectAdapter.getItem(position);
        if (item != null) {
            if (connectAdapter.checkConnected(item.getTitle())) {
                Toast.makeText(getContext(), "Drone đang kết nối", Toast.LENGTH_SHORT).show();
            } else {
                showDialog(item.getTitle());
            }
        }


    }

    private void showDialog(final String title, final int position) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edt);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText edtPass = dialog.findViewById(R.id.edtPass);
        final ImageView imgView = dialog.findViewById(R.id.imgView);
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                    case MotionEvent.ACTION_UP:
                        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                }
                return true;
            }
        });
        dialog.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPass.getText().toString().equals("123456")) {

                    Toast.makeText(getContext(), "Kết nối thành công với Drone " + title, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    bus.post(AppAction.DONE_STEP_1);
                    ItemConnect itemConnect = (ItemConnect) connectAdapter.getItem(position);


                } else {
                    Toast.makeText(getContext(), "Mật khẩu chưa đúng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvTitle.setText(title);
        dialog.show();
    }

    private void showDialog(final String title) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edt);
        TextView tvTitle = dialog.findViewById(R.id.tvTitle);
        final EditText edtPass = dialog.findViewById(R.id.edtPass);
        final ImageView imgView = dialog.findViewById(R.id.imgView);
        imgView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                    case MotionEvent.ACTION_UP:
                        edtPass.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                    case MotionEvent.ACTION_MOVE:
                        edtPass.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                        edtPass.setSelection(edtPass.length());
                        break;
                }
                return true;
            }
        });
        dialog.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPass.getText().toString().equals("123456")) {

                    Toast.makeText(getContext(), "Kết nối thành công với Drone " + title, Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    bus.post(AppAction.DONE_STEP_1);
                    connectAdapter.connectDrone(title);


                } else {
                    Toast.makeText(getContext(), "Mật khẩu chưa đúng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvTitle.setText(title);
        dialog.show();
    }

    private void dummy() {
        items.clear();

        items.add(new HeaderConnect("Drone có sẵn"));
        items.add(new ItemConnect("DR118992922", false));
        items.add(new ItemConnect("DR118903022", false));
        items.add(new ItemConnect("DR128994922", false));
        items.add(new ItemConnect("DR3389249922", false));
        items.add(new ItemConnect("DR1189494922", false));
        items.add(new ItemConnect("DR1189044222", false));
        items.add(new ItemConnect("DR1289992222", false));
        items.add(new ItemConnect("DR3389944922", false));

        itemsConnect.clear();
        itemsConnect.add(new ItemConnect("DR118992922", false));
        itemsConnect.add(new ItemConnect("DR118903022", false));
        itemsConnect.add(new ItemConnect("DR128994922", false));
        itemsConnect.add(new ItemConnect("DR3389249922", false));
        itemsConnect.add(new ItemConnect("DR1189494922", false));
        itemsConnect.add(new ItemConnect("DR1189044222", false));
        itemsConnect.add(new ItemConnect("DR1289992222", false));
        itemsConnect.add(new ItemConnect("DR3389944922", false));
    }
}
