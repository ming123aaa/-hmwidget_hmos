package com.ohuang.hmwidget.fragment;

import java.util.ArrayList;

public abstract class FragmentTransaction {
    static final int OP_NULL = 0;
    static final int OP_ADD = 1;
    static final int OP_REPLACE = 2;
    static final int OP_REMOVE = 3;
    static final int OP_HIDE = 4;
    static final int OP_SHOW = 5;
    static final int OP_ATTACH = 6;

    static final class Op {
        int mCmd;
        Fragment mFragment;

        Op() {
        }

        Op(int cmd, Fragment fragment) {
            this.mCmd = cmd;
            this.mFragment = fragment;
        }
    }

    ArrayList<Op> mOps = new ArrayList<>();

    void addOp(Op op) {
        mOps.add(op);
    }


    /**
     * @param id       容器ID ComponentContainer ID
     *
     * @param fragment Fragment
     * @param tag
     * @return
     */
    public FragmentTransaction add(int id, Fragment fragment, String tag) {
        doAddOp(id, fragment, tag);
        return this;
    }

    public FragmentTransaction add(int id, Fragment fragment) {
        add(id, fragment, null);
        return this;
    }

    public FragmentTransaction attach(Fragment fragment) {
        attach(fragment,null);
        return this;
    }

    public FragmentTransaction attach(Fragment fragment,String tag) {
        doAttachOp(fragment,tag);
        return this;
    }

    public FragmentTransaction replace(int id, Fragment fragment) {
        doReplaceOp(id, fragment, null);
        return this;
    }

    public FragmentTransaction replace(int id, Fragment fragment, String tag) {
        doReplaceOp(id, fragment, tag);
        return this;
    }

    public FragmentTransaction remove(Fragment fragment) {
        doRemoveOp(fragment);
        return this;
    }

    public FragmentTransaction show(Fragment fragment) {
        doShowOp(fragment);
        return this;
    }

    public FragmentTransaction hide(Fragment fragment) {
        doHideOp(fragment);
        return this;
    }


    void doShowOp(Fragment fragment) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        addOp(new Op(OP_SHOW, fragment));
    }

    void doHideOp(Fragment fragment) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        addOp(new Op(OP_HIDE, fragment));
    }

    void doRemoveOp(Fragment fragment) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        addOp(new Op(OP_REMOVE, fragment));
    }

    void doReplaceOp(int id, Fragment fragment, String tag) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        if (tag != null) {
            fragment.mTag = tag;
        }
        fragment.mId = id;
        addOp(new Op(OP_REPLACE, fragment));
    }

    void doAddOp(int id, Fragment fragment, String tag) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        if (tag != null) {
            fragment.mTag = tag;
        }
        fragment.mId = id;
        addOp(new Op(OP_ADD, fragment));
    }

    void doAttachOp(Fragment fragment, String tag) {
        if (fragment == null) {
            throw new IllegalStateException("fragment is Null");
        }
        if (tag != null) {
            fragment.mTag = tag;
        }
        addOp(new Op(OP_ATTACH, fragment));
    }


    public abstract void commit();


    public abstract void commitNow();

}
