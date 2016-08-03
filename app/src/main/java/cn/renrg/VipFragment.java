package cn.renrg;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Hello on 2015/8/27.
 */
public class VipFragment extends Fragment {

    @BindView(R.id.vip_list)
    ListView vipList;
    private View rootView;
    private VipAdapter vipAdapter;
    private ArrayList<Vip> vips;

    public static VipFragment newInstance() {
        return new VipFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_vip, container, false);
            ButterKnife.bind(this, rootView);
            initView();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void initView() {
        vips = new ArrayList<>();
        getContacts();
        vipAdapter = new VipAdapter(getActivity(), vips);
        vipList.setAdapter(vipAdapter);
    }

    private void getContacts() {
        Cursor cursor = this.getContext().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int contactIdIndex = 0;
        int nameIndex = 0;

        if (cursor.getCount() > 0) {
            contactIdIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
        }
        while (cursor.moveToNext()) {
            Vip vip = new Vip();
            String contactId = cursor.getString(contactIdIndex);
            vip.name = cursor.getString(nameIndex);
            /*
             * 查找该联系人的phone信息
             */
            Cursor phones = this.getContext().getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                    null,
                    ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                    null, null);
            int phoneIndex = 0;
            if (phones.getCount() > 0) {
                phoneIndex = phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            }
            while (phones.moveToNext()) {
                vip.mobile = phones.getString(phoneIndex);
            }
            if (!TextUtils.isEmpty(vip.name) && !TextUtils.isEmpty(vip.mobile))
                vips.add(vip);
        }
    }
}
