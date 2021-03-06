package com.vless.wificam.adapts;

import java.util.List;

import com.vless.wificam.R;
import com.vless.wificam.frags.WifiCamFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;

/**
 * Created with IntelliJ IDEA.
 * Author: wangjie  email:tiantian.china.2@gmail.com
 * Date: 13-10-10
 * Time: 涓婂�?:25
 */
public class FragmentTabAdapter implements RadioGroup.OnCheckedChangeListener{
    private List<WifiCamFragment> fragments; // 锟�?涓猼ab椤甸潰�?瑰簲锟�?涓狥ragment
    private RadioGroup rgs; // 鐢ㄤ簬鍒囨崲tab
    private FragmentActivity fragmentActivity; // Fragment锟�?灞炵殑Activity
    private int fragmentContentId; // Activity涓墍瑕佽鏇挎崲鐨勫尯鍩熺殑id

    private int currentTab; // 褰撳墠Tab椤甸潰绱㈠紩

    private OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener; // 鐢ㄤ簬璁╄皟鐢�?锟藉湪鍒囨崲tab鏃讹�?锟藉鍔犳柊鐨勫姛锟�?

    public FragmentTabAdapter(FragmentActivity fragmentActivity, List<WifiCamFragment> fragments, int fragmentContentId, RadioGroup rgs) {
        this.fragments = fragments;
        this.rgs = rgs;
        this.fragmentActivity = fragmentActivity;
        this.fragmentContentId = fragmentContentId;

        // 榛樿鏄剧ず绗竴锟�?
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        ft.add(fragmentContentId, fragments.get(0));
        ft.commit();

        rgs.setOnCheckedChangeListener(this);


    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
        for(int i = 0; i < rgs.getChildCount(); i++){
            if(rgs.getChildAt(i).getId() == checkedId){
                Fragment fragment = fragments.get(i);
                FragmentTransaction ft = obtainFragmentTransaction(i);

                getCurrentFragment().onPause(); // 鏆傚仠褰撳墠tab
//                getCurrentFragment().onStop(); // 鏆傚仠褰撳墠tab

                if(fragment.isAdded()){
//                    fragment.onStart(); // 鍚姩鐩爣tab鐨刼nStart()
                    fragment.onResume(); // 鍚姩鐩爣tab鐨刼nResume()
                }else{
                    ft.add(fragmentContentId, fragment);
                }
                showTab(i); // 鏄剧ず鐩爣tab
                ft.commit();

                // 濡傛灉璁剧疆浜嗗垏鎹ab棰濆鍔熻兘鍔熻兘鎺ュ彛
                if(null != onRgsExtraCheckedChangedListener){
                    onRgsExtraCheckedChangedListener.OnRgsExtraCheckedChanged(radioGroup, checkedId, i);
                }

            }
        }

    }

    /**
     * 鍒囨崲tab
     * @param idx
     */
    private void showTab(int idx){
        for(int i = 0; i < fragments.size(); i++){
            Fragment fragment = fragments.get(i);
            FragmentTransaction ft = obtainFragmentTransaction(idx);

            if(idx == i){
                ft.show(fragment);
            }else{
                ft.hide(fragment);
            }
            ft.commit();
        }
        currentTab = idx; // 鏇存柊鐩爣tab涓哄綋鍓峵ab
    }

    /**
     * 鑾峰彇锟�?涓甫鍔ㄧ敾鐨凢ragmentTransaction
     * @param index
     * @return
     */
    private FragmentTransaction obtainFragmentTransaction(int index){
        FragmentTransaction ft = fragmentActivity.getSupportFragmentManager().beginTransaction();
        // 璁剧疆鍒囨崲鍔ㄧ�?
        if(index > currentTab){
            ft.setCustomAnimations(R.anim.slide_left_in, R.anim.slide_left_out);
        }else{
            ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_right_out);
        }
        return ft;
    }

    public int getCurrentTab() {
        return currentTab;
    }

    public Fragment getCurrentFragment(){
        return fragments.get(currentTab);
    }

    public OnRgsExtraCheckedChangedListener getOnRgsExtraCheckedChangedListener() {
        return onRgsExtraCheckedChangedListener;
    }

    public void setOnRgsExtraCheckedChangedListener(OnRgsExtraCheckedChangedListener onRgsExtraCheckedChangedListener) {
        this.onRgsExtraCheckedChangedListener = onRgsExtraCheckedChangedListener;
    }

    /**
     *  鍒囨崲tab棰濆鍔熻兘鍔熻兘鎺ュ彛
     */
    public static class OnRgsExtraCheckedChangedListener{
        public void OnRgsExtraCheckedChanged(RadioGroup radioGroup, int checkedId, int index){

        }
    }

}
