package com.masoud.base_mvp_module.utils;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.masoud.base_mvp_module.MVP_BaseFragment;


/**
 * Created by Masoud pc on 8/5/2018.
 */
public class MVP_FragmentUtils {

    private Builder builder;


    public MVP_FragmentUtils transaction() {

        if (builder.destinationFragment != null && builder.fragmentManager != null) {

            FragmentTransaction trans;
            trans = builder.fragmentManager.beginTransaction();

            String tag = builder.destinationFragment.getTag();

            if (builder.destinationFragment.getTag() == null)
                tag = builder.destinationFragment.getClass().getSimpleName().concat("_").concat(String.valueOf(System.currentTimeMillis()));

            if (builder.MVVMAnimationUtils != null) {
                trans.setCustomAnimations(builder.MVVMAnimationUtils.getEnterAnimation(), builder.MVVMAnimationUtils.getExitAnimation());
            }
            trans.replace(builder.containerViewID, builder.destinationFragment, tag);

            if (builder.isAddToBackStack) {
                trans.addToBackStack(tag);
            }
            trans.commit();


        }


        return this;
    }


    public static class Builder {

        private MVP_BaseFragment destinationFragment;
        private boolean isAddToBackStack;
        private FragmentManager fragmentManager;
        private AnimationUtils MVVMAnimationUtils;
        private int containerViewID;


        public Builder setDestinationFragment(MVP_BaseFragment destinationFragment) {
            this.destinationFragment = destinationFragment;
            return this;
        }

        public Builder setAddToBackStack(boolean addToBackStack) {
            isAddToBackStack = addToBackStack;
            return this;
        }


        public Builder setFragmentManager(FragmentManager fragmentManager) {
            this.fragmentManager = fragmentManager;
            return this;
        }

        public Builder setMVVMAnimationUtils(AnimationUtils MVVMAnimationUtils) {
            this.MVVMAnimationUtils = MVVMAnimationUtils;
            return this;

        }

        public Builder setContainerViewID(int containerViewID) {
            this.containerViewID = containerViewID;
            return this;

        }


        public MVP_FragmentUtils create() {

            MVP_FragmentUtils fragmentManagement = new MVP_FragmentUtils();
            fragmentManagement.builder = this;
            return fragmentManagement;
        }
    }

}
