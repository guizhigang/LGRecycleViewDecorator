package com.gui.materialdesign;

import android.test.FlakyTest;
import android.util.Log;

import junit.framework.TestCase;

/**
 * Created by guizhigang on 16/12/27.
 */
public class MainActivityTest extends TestCase {

    public void setUp() throws Exception {
        super.setUp();
        Log.d("MainActivityTest","setUp");
    }

    public void tearDown() throws Exception {
        Log.d("MainActivityTest","tearDown");
    }

    @FlakyTest
    public void test2() {
        Log.d("MainActivityTest","tearDown");
    }
}