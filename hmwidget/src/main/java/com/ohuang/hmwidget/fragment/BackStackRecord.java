package com.ohuang.hmwidget.fragment;

import ohos.eventhandler.EventHandler;
import ohos.eventhandler.EventRunner;
import ohos.eventhandler.InnerEvent;

class BackStackRecord extends FragmentTransaction {
    FragmentManager fragmentManager;
    private volatile boolean isCommit=false;
    static final int Msg_Code_Commit=1;

    BackStackRecord(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }


    @Override
    public void commit() {
        eventHandler.sendEvent(Msg_Code_Commit);
    }

    EventHandler eventHandler=new EventHandler(EventRunner.getMainEventRunner()){
        @Override
        protected void processEvent(InnerEvent event) {
            switch (event.eventId){
                case Msg_Code_Commit:
                    commitNow();
                    break;
            }
        }
    };

    @Override
    public void commitNow() {
            if (isCommit) {
                throw new RuntimeException("FragmentTransaction 正在提交中 请勿重复提交");
            } else {
                isCommit = true;
                runOp();
                isCommit = false;
            }
    }

    void runOp() {
        for (Op mOp : mOps) {
            switch (mOp.mCmd) {
                case OP_NULL:
                    break;
                case OP_ADD:
                    fragmentManager.addFragment(mOp.mFragment);
                    break;
                case OP_REMOVE:
                    fragmentManager.removeFragment(mOp.mFragment);
                    break;
                case OP_REPLACE:
                    fragmentManager.replaceFragment(mOp.mFragment);
                    break;
                case OP_HIDE:
                    fragmentManager.hideFragment(mOp.mFragment);
                    break;
                case OP_SHOW:
                    fragmentManager.showFragment(mOp.mFragment);
                    break;
                case OP_ATTACH:
                    fragmentManager.attachFragment(mOp.mFragment);
                    break;

            }
        }
        mOps.clear();
    }
}
