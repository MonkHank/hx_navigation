package com.seuic.hisense.utils;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.util.SparseArray;

import com.seuic.hisense.fragments.BaseFragment;


public class FragmentHelper {
	private static final int NO_TAB = -1;
	private static final int STACK_SIZE = 10;
	private static FragmentHelper mHelper;
	private static FragmentManager mManager;
	private static int mFrameId;
	private static SparseArray<FragmentStack> arrayStack;
	private static int mCurTab = -1;
	private static FragmentFactory mFactory;
    public   static boolean callBack = false;
	
	static{
		arrayStack = new SparseArray<FragmentStack>();
	}

	public static void initHelper(Activity activity , int flId){
        mHelper = new FragmentHelper();
		mManager = activity.getFragmentManager();
		mFrameId = flId;
		// 获取fragment的工厂类
		mFactory = FragmentFactory.getInstance();
        arrayStack = new SparseArray<FragmentStack>();
	}
	
	public static FragmentHelper getInstance(){
		return mHelper;
	}
	
	public synchronized BaseFragment getCurFragment(){
		FragmentStack stack = arrayStack.get(mCurTab, null);
		if(stack == null){
			return null;
		}
		return stack.getCurFragment();
	}

   /*public static boolean IsScanFragment = false;

    //是否是扫描框架
    public   void JudgeScanFragment(){
        if(getCurFragment() instanceof ScanFragment){
            IsScanFragment = true;
        }
        else {
            IsScanFragment = false;
        }
    }*/

    //跳转到下一个界面
	public void transcateFoward(int fmId){
		transcateFoward(mFactory.getFragment(fmId));
	}
	
	public synchronized void transcateFoward(BaseFragment showFragment){
        if(mCurTab==-1){
            mCurTab = 0;
        }
        callBack = false;
		FragmentStack stack = arrayStack.get(mCurTab, null);
		if(stack == null){
			stack = new FragmentStack(STACK_SIZE);
			arrayStack.put(mCurTab, stack);
		}
		FragmentTransaction transaction = mManager.beginTransaction();
		if(stack.getCurFragment() != null){
			transaction.hide(stack.getCurFragment());
			stack.getCurFragment().onPause();
		}
		transaction.add(mFrameId, showFragment);
		stack.push(showFragment);
		transaction.commitAllowingStateLoss();
        callBack = false;
	}

    //返回 不带参数传回
	public void transcateBack(){
		transcateBackOnResult(null);
	}

    //返回 带参数传回
	public synchronized void transcateBackOnResult(Object object){
        callBack = false;
		FragmentStack stack = arrayStack.get(mCurTab, null);
		
		FragmentTransaction transaction = mManager.beginTransaction();

        stack.getCurFragment().onPause();//停止当前页面 测试
		transaction.remove(stack.getCurFragment());

		transaction.show(stack.pop());
		stack.getCurFragment().onResume();
		transaction.commitAllowingStateLoss();
		stack.getCurFragment().callBackOnResult(object);
        callBack = true;
	}

    //直接跳跃返回到前面的界面
	public synchronized  void transcateRollBack(int fmId){
		FragmentStack stack = arrayStack.get(mCurTab, null);
		BaseFragment[] fragments = stack.getAllFragments();

		FragmentTransaction transaction = mManager.beginTransaction();
		for(int i = fragments.length - 1; i > -1 ; i--){
			if(null == fragments[i]){
				continue;
			}

			if(mFactory.getFragment(fmId).getClass().equals(fragments[i].getClass())){
				transaction.show(stack.getFragment(i));
                transaction.commitAllowingStateLoss();
			}
			else{
				transaction.remove(stack.getCurFragment());
				stack.pop();
			}
		}
	}

    //直接跳跃返回到前面的界面
public synchronized void transcateRollBack(BaseFragment fragment){
		FragmentStack stack = arrayStack.get(mCurTab, null);
		BaseFragment[] fragments = stack.getAllFragments();

		FragmentTransaction transaction = mManager.beginTransaction();
		for(int i = fragments.length - 1; i > -1 ; i--){
			if(null == fragments[i]){
				continue;
			}

			if(fragment.getClass().equals(fragments[i].getClass())){
				transaction.show(stack.getFragment(i));
				transaction.commitAllowingStateLoss();
				return;
			}
			else{
				transaction.remove(stack.getCurFragment());
				stack.pop();
			}
		}
		transaction.commitAllowingStateLoss();
	}

    //主菜单tab切换
	public void transcateOnTab(int newTab , int fmId){
        transcateOnTab(newTab,mFactory.getFragment(fmId));
    }
	
	public synchronized void transcateOnTab(int newTab , BaseFragment defaultFragment){
        LogHelper.i("newTab",newTab+"");
        LogHelper.i("defaultFragment",defaultFragment.toString());
		if(mCurTab == NO_TAB){
			replace(defaultFragment, newTab);
			return;
		}
        LogHelper.i("transcateOnTab","transcateOnTab");
		FragmentStack newStack = arrayStack.get(newTab, null);
		if(newStack == null){
			newStack = new FragmentStack(STACK_SIZE);
			arrayStack.put(newTab, newStack);
		}
		
		FragmentStack oldStack = arrayStack.get(mCurTab);
		
		FragmentTransaction transaction = mManager.beginTransaction();
        if(oldStack!=null&&oldStack.getCurFragment()!=null){//oldStack!=null
            transaction.hide(oldStack.getCurFragment());
            oldStack.getCurFragment().onPause();//停止当前页面 测试
        }

		
		BaseFragment tempShowFragment = null;
		if(newStack.getCurIndex() == -1||newStack.getAllFragments().length==0){
			tempShowFragment = defaultFragment;
			transaction.add(mFrameId, tempShowFragment);
            newStack.setCurIndex();//防止CurIndex和实际长度不符
			newStack.push(tempShowFragment);
		}
		else{

			tempShowFragment = newStack.getCurFragment();
            LogHelper.i("tempShowFragment",tempShowFragment+"");
            if(tempShowFragment!=null){
                transaction.show(tempShowFragment);
                tempShowFragment.onResume();//恢复当前页面 测试
            }

		}
		transaction.commitAllowingStateLoss();
		mCurTab = newTab;
	}
	
	public void replace(int fmId){
        replace(mFactory.getFragment(fmId));
    }
	
	public synchronized void replace(BaseFragment fragment){
        FragmentStack stack = arrayStack.get(mCurTab, null);
        if(stack == null){
            stack = new FragmentStack(STACK_SIZE);
            arrayStack.put(mCurTab, stack);
        }
        FragmentTransaction transaction = mManager.beginTransaction();
        transaction.remove(stack.getCurFragment());
        stack.pop();
        transaction.add(mFrameId, fragment);
        transaction.show(fragment);
        stack.push(fragment);
        transaction.commitAllowingStateLoss();
    }
	
	public void replace(int fmId , int tabId){
		replace(mFactory.getFragment(fmId), tabId);
	}
	
	public synchronized void replace(BaseFragment fragment , int tabId){
		FragmentStack stack = arrayStack.get(tabId, null);
		if(stack == null){
			stack = new FragmentStack(STACK_SIZE);
			arrayStack.put(tabId, stack);
		}
		FragmentTransaction transaction = mManager.beginTransaction();
		transaction.replace(mFrameId, fragment);
		stack.push(fragment);
		transaction.commitAllowingStateLoss();
		mCurTab = tabId;
	}
	
	public void release(){
		mCurTab = NO_TAB;
		arrayStack.clear();
	}
	
	public int getmCurTab(){
		return mCurTab;
	}
	
	class FragmentStack{
		private BaseFragment[] fragments;
		private int curIndex = -1;
		
		FragmentStack(int size){
			fragments = new BaseFragment[size];
		}
		
		public void push(BaseFragment fragment){
			if(curIndex < fragments.length - 1){
				fragments[++curIndex] = fragment;
			}
		}
		
		public BaseFragment pop(){
            if(curIndex > 0){//if(curIndex > -1){
				fragments[curIndex--] = null;
				return fragments[curIndex] ;
			}
			return null;
		}
		
		public void replace(BaseFragment fragment){
			fragments[curIndex] = fragment;
		}
		
		public BaseFragment getCurFragment(){
            if(curIndex==-1){
                curIndex = 0;
            }
			return fragments[curIndex];
		}
		
		public int getCurIndex(){
			return curIndex;
		}
		
		public BaseFragment[] getAllFragments(){
			return fragments;
		}
		
		public BaseFragment getFragment(int index){
			return fragments[index];
		}

        public void setCurIndex(){
            curIndex = -1;
        }
	}
}
