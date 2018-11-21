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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.manage.drone.R;
import com.manage.drone.adapter.BaseRecycleViewAdapter;
import com.manage.drone.adapter.ConnectAdapter;
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

public class ConnectFragment extends BaseFragment implements BaseRecycleViewAdapter.ItemClickListener {
    public static ConnectFragment newInstance() {
        Bundle args = new Bundle();
        ConnectFragment fragment = new ConnectFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.rcvList)
    RecyclerView rcvList;
    ConnectAdapter connectAdapter;
    List<BaseItemModel> items = new ArrayList<>();

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

    }

    @Override
    public void onItemClick(int position) {
        BaseItemModel item = connectAdapter.getItem(position);
        if (item != null) {
            showDialog(item.getTitle());
        }


    }

    private void showDialog(final String title) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater)
                getActivity().getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_edt, null);
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        final EditText edtPass = view.findViewById(R.id.edtPass);
        final ImageView imgView = view.findViewById(R.id.imgView);
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
        view.findViewById(R.id.btn_connect).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (edtPass.getText().toString().equals("123456")) {
                    dialog.dismiss();
                    Toast.makeText(getContext(), "Kết nối thành công với Drone " + title, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Mật khẩu chưa đúng, vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        view.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    dialog.dismiss();
            }
        });
        tvTitle.setText(title);
        dialog.setContentView(view);
        dialog.show();
    }

    private void dummy() {
        items.clear();
        items.add(new HeaderConnect("Đã kết nối"));
        items.add(new ItemConnect("DR11899922"));
        items.add(new ItemConnect("DR11890022"));
        items.add(new ItemConnect("DR12899922"));
        items.add(new ItemConnect("DR33899922"));
        items.add(new HeaderConnect("Drone có sẵn"));
        items.add(new ItemConnect("DR118992922"));
        items.add(new ItemConnect("DR118903022"));
        items.add(new ItemConnect("DR128994922"));
        items.add(new ItemConnect("DR3389249922"));
        items.add(new ItemConnect("DR1189494922"));
        items.add(new ItemConnect("DR1189044222"));
        items.add(new ItemConnect("DR1289992222"));
        items.add(new ItemConnect("DR3389944922"));
    }
}
