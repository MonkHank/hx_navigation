package com.seuic.hisense.utils;

import android.util.SparseArray;

import com.seuic.hisense.fragments.BaseFragment;
import com.seuic.hisense.fragments.MainFragment;
import com.seuic.hisense.fragments.caigou.caigouMainFragment;
import com.seuic.hisense.fragments.caigou.caigouOpenBillFragment;
import com.seuic.hisense.fragments.caigou.caigouQueryScanFragment;
import com.seuic.hisense.fragments.caigou.caigouScanFragment;
import com.seuic.hisense.fragments.pandian.pandianMainFragment;
import com.seuic.hisense.fragments.pandian.pandianOpenBillFragment;
import com.seuic.hisense.fragments.pandian.pandianQueryScanFragment;
import com.seuic.hisense.fragments.pandian.pandianScanFragment;
import com.seuic.hisense.fragments.peisong.peisongMainFragment;
import com.seuic.hisense.fragments.peisong.peisongOpenBillFragment;
import com.seuic.hisense.fragments.peisong.peisongQueryScanFragment;
import com.seuic.hisense.fragments.peisong.peisongScanFragment;
import com.seuic.hisense.fragments.pifatuihuo.pifatuihuoMainFragment;
import com.seuic.hisense.fragments.pifatuihuo.pifatuihuoOpenBillFragment;
import com.seuic.hisense.fragments.pifatuihuo.pifatuihuoQueryScanFragment;
import com.seuic.hisense.fragments.pifatuihuo.pifatuihuoScanFragment;
import com.seuic.hisense.fragments.pifaxiaoshou.pifaxiaoshouMainFragment;
import com.seuic.hisense.fragments.pifaxiaoshou.pifaxiaoshouOpenBillFragment;
import com.seuic.hisense.fragments.pifaxiaoshou.pifaxiaoshouQueryScanFragment;
import com.seuic.hisense.fragments.pifaxiaoshou.pifaxiaoshouScanFragment;
import com.seuic.hisense.fragments.tiaobo.tiaoboMainFragment;
import com.seuic.hisense.fragments.tiaobo.tiaoboOpenBillFragment;
import com.seuic.hisense.fragments.tiaobo.tiaoboQueryScanFragment;
import com.seuic.hisense.fragments.tiaobo.tiaoboScanFragment;
import com.seuic.hisense.fragments.tuihuo.tuihuoMainFragment;
import com.seuic.hisense.fragments.tuihuo.tuihuoOpenBillFragment;
import com.seuic.hisense.fragments.tuihuo.tuihuoQueryScanFragment;
import com.seuic.hisense.fragments.tuihuo.tuihuoScanFragment;
import com.seuic.hisense.fragments.yanshou.yanshouMainFragment;
import com.seuic.hisense.fragments.yanshou.yanshouOpenBillFragment;
import com.seuic.hisense.fragments.yanshou.yanshouQueryScanFragment;
import com.seuic.hisense.fragments.yanshou.yanshouScanFragment;
import com.seuic.hisense.fragments.yaohuo.yaohuoMainFragment;
import com.seuic.hisense.fragments.yaohuo.yaohuoOpenBillFragment;
import com.seuic.hisense.fragments.yaohuo.yaohuoQueryScanFragment;
import com.seuic.hisense.fragments.yaohuo.yaohuoScanFragment;
import com.seuic.hisense.settings.DataDownloadFragment;
import com.seuic.hisense.settings.SetFragment;

/**
 * fragment工厂类，添加fragment到sparseArray；
 */
public class FragmentFactory {

    public static final int Main_Fragment 					=  0;
    public static final int Admin_Fragment                  =  1;
    public static final int Set_Fragment                    =  2;
	public static final int DataDownload_Fragment            =  3;

    public static final int pandianMain_Fragment                  = 100;//盘点主界面
    public static final int pandianOpenBill_Fragment                  = 101;//盘点开单
	public static final int pandianScan_Fragment                  = 102;//盘点扫描
	public static final int pandianQueryScan_Fragment                  = 103;//盘点查询

	public static final int caigouMain_Fragment 				  = 200; // 采购主界面
	public static final int caigouOpenBill_Fragment 				  = 201; // 采购开单
	public static final int caigouScan_Fragment 				  = 202; // 采购扫描
	public static final int caigouQuery_Fragment 				  = 203; // 采购查询


	public static final int yanshouMain_Fragment 				  = 300; // 验收主界面
	public static final int yanshouOpenBill_Fragment 				  = 301; // 验收开单
	public static final int yanshouScan_Fragment 				  = 302; // 验收扫描
	public static final int yanshouQuery_Fragment 				  = 303; // 验收查询

	public static final int tuihuoMain_Fragment 				  = 400; // 退货主界面
	public static final int tuihuoOpenBill_Fragment 				  = 401; // 退货开单
	public static final int tuihuoScan_Fragment 				  = 402; // 退货扫描
	public static final int tuihuoQuery_Fragment 				  = 403; // 退货查询

	public static final int yaohuoMain_Fragment 				  = 500; // 要货主界面
	public static final int yaohuoOpenBill_Fragment 				  = 501; // 要货开单
	public static final int yaohuoScan_Fragment 				  = 502; // 要货扫描
	public static final int yaohuoQuery_Fragment 				  = 503; // 要货查询

	public static final int peisongMain_Fragment 				  = 600; // 配送主界面
	public static final int peisongOpenBill_Fragment 				  = 601; // 配送开单
	public static final int peisongScan_Fragment				  =603; // 配送扫描
	public static final int peisongQuery_Fragment				  =604; // 配送查询

	public static final int pifaxiaoshouMain_Fragment 				  = 700; // 批发销售主界面
	public static final int pifaxiaoshouOpenBill_Fragment 				  = 701; // 批发销售开单
	public static final int pifaxiaoshouScan_Fragment 				  = 702; // 批发销售扫描
	public static final int pifaxiaoshouQuery_Fragment 				  = 703; // 批发销售查询


	public static final int pifatuihuoMain_Fragment 				  = 800; // 批发退货主界面
	public static final int pifatuihuoOpenBill_Fragment 				  = 801; // 批发退货开单
	public static final int pifatuihuoScan_Fragment 				  = 802; // 批发退货扫描
	public static final int pifatuihuoQuery_Fragment 				  = 803; // 批发退货查询

	public static final int tiaoboMain_Fragment 				  = 900; // 店间调拨主界面
	public static final int tiaoboOpenBill_Fragment 				  = 901; // 店间调拨开单
	public static final int tiaoboScan_Fragment 				  = 902; // 店间调拨扫描
	public static final int tiaoboQuery_Fragment 				  = 903; // 店间调拨查询







	private static SparseArray<Class<? extends BaseFragment>> fragmentClsBindId;
	private static FragmentFactory factory;

	static{
        fragmentClsBindId = new SparseArray<Class<? extends BaseFragment>>();
        fragmentClsBindId.put(Main_Fragment, MainFragment.class);
		fragmentClsBindId.put(Set_Fragment, SetFragment.class);
		fragmentClsBindId.put(DataDownload_Fragment, DataDownloadFragment.class);
       /* fragmentClsBindId.put(Main_Fragment, MainFragment.class);
        fragmentClsBindId.put(TakeDispatch_Fragment, TakeDispatchFragment.class);//取派员
        fragmentClsBindId.put(YYB_Fragment, YYBFragment.class);//营业部
        fragmentClsBindId.put(YYB_Old_Fragment, YYB_OldFragment.class);//营业部（旧版本）*/


        fragmentClsBindId.put(pandianMain_Fragment, pandianMainFragment.class);
        fragmentClsBindId.put(pandianOpenBill_Fragment, pandianOpenBillFragment.class);
		fragmentClsBindId.put(pandianScan_Fragment, pandianScanFragment.class);
		fragmentClsBindId.put(pandianQueryScan_Fragment, pandianQueryScanFragment.class);

		fragmentClsBindId.put(caigouMain_Fragment,caigouMainFragment.class);// 采购主界面
		fragmentClsBindId.put(caigouOpenBill_Fragment,caigouOpenBillFragment.class);// 采购开单
		fragmentClsBindId.put(caigouScan_Fragment,caigouScanFragment.class);// 采购扫描
		fragmentClsBindId.put(caigouQuery_Fragment,caigouQueryScanFragment.class);// 采购查询

		fragmentClsBindId.put(yanshouMain_Fragment,yanshouMainFragment.class);// 验收主界面
		fragmentClsBindId.put(yanshouOpenBill_Fragment,yanshouOpenBillFragment.class);// 验收开单
		fragmentClsBindId.put(yanshouScan_Fragment,yanshouScanFragment.class);// 验收扫描
		fragmentClsBindId.put(yanshouQuery_Fragment,yanshouQueryScanFragment.class);// 验收查询

		fragmentClsBindId.put(tuihuoMain_Fragment,tuihuoMainFragment.class);// 退货主界面
		fragmentClsBindId.put(tuihuoOpenBill_Fragment,tuihuoOpenBillFragment.class);// 退货开单
		fragmentClsBindId.put(tuihuoScan_Fragment,tuihuoScanFragment.class);// 退货扫描
		fragmentClsBindId.put(tuihuoQuery_Fragment,tuihuoQueryScanFragment.class);// 退货查询

		fragmentClsBindId.put(yaohuoMain_Fragment,yaohuoMainFragment.class);// 要货主界面
		fragmentClsBindId.put(yaohuoOpenBill_Fragment,yaohuoOpenBillFragment.class);// 要货开单
		fragmentClsBindId.put(yaohuoScan_Fragment, yaohuoScanFragment.class);// 要货扫描
		fragmentClsBindId.put(yaohuoQuery_Fragment,yaohuoQueryScanFragment.class);// 要货查询

		fragmentClsBindId.put(peisongMain_Fragment,peisongMainFragment.class);// 配送主界面
		fragmentClsBindId.put(peisongOpenBill_Fragment,peisongOpenBillFragment.class);// 配送开单
		fragmentClsBindId.put(peisongScan_Fragment,peisongScanFragment.class);// 配送扫描
		fragmentClsBindId.put(peisongQuery_Fragment,peisongQueryScanFragment.class);// 配送查询

		fragmentClsBindId.put(pifaxiaoshouMain_Fragment,pifaxiaoshouMainFragment.class);// 批发主界面
		fragmentClsBindId.put(pifaxiaoshouOpenBill_Fragment,pifaxiaoshouOpenBillFragment.class);// 批发开单
		fragmentClsBindId.put(pifaxiaoshouScan_Fragment,pifaxiaoshouScanFragment.class);// 批发扫描
		fragmentClsBindId.put(pifaxiaoshouQuery_Fragment,pifaxiaoshouQueryScanFragment.class);// 批发查询

		fragmentClsBindId.put(pifatuihuoMain_Fragment,pifatuihuoMainFragment.class);// 批发退货主界面
		fragmentClsBindId.put(pifatuihuoOpenBill_Fragment,pifatuihuoOpenBillFragment.class);// 批发退货开单
		fragmentClsBindId.put(pifatuihuoScan_Fragment,pifatuihuoScanFragment.class);// 批发退货扫描
		fragmentClsBindId.put(pifatuihuoQuery_Fragment, pifatuihuoQueryScanFragment.class);// 批发退货查询

		fragmentClsBindId.put(tiaoboMain_Fragment,tiaoboMainFragment.class);// 调拨主界面
		fragmentClsBindId.put(tiaoboOpenBill_Fragment, tiaoboOpenBillFragment.class);// 调拨开单
		fragmentClsBindId.put(tiaoboScan_Fragment, tiaoboScanFragment.class);// 调拨扫描
		fragmentClsBindId.put(tiaoboQuery_Fragment, tiaoboQueryScanFragment.class);// 调拨查询


	}
	
	public static FragmentFactory getInstance(){
		if(factory == null){
			factory = new FragmentFactory();
		}
		return factory;
	}
	
	public BaseFragment getFragment(int fmId) {
		BaseFragment fragment = null;
		try {
			// 得到fragment的实例
			fragment = fragmentClsBindId.get(fmId).newInstance();
		} catch (Exception e) {
            LogHelper.i("FragmentFactory","getFragment");
		}
		return fragment;
	}
}
