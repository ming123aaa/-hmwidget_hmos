package com.ohuang.android;

import com.ohuang.android.slice.MainAbilitySlice;
import ohos.aafwk.ability.Ability;
import ohos.aafwk.content.Intent;
import ohos.utils.PacMap;

public class MainAbility extends Ability {
    @Override
    public void onStart(Intent intent) {
        super.onStart(intent);
        super.setMainRoute(MainAbilitySlice.class.getName());

    }

    @Override
    public void onSaveAbilityState(PacMap outState) {
        super.onSaveAbilityState(outState);

    }
}
