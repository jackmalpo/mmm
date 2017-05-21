package com.malpo.mmm

import com.intellij.testFramework.fixtures.CodeInsightTestUtil
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase

class RemoveHungarianTest : LightPlatformCodeInsightFixtureTestCase() {


    override fun getTestDataPath(): String = "testData/com/malpo/mmm"

    fun testFormatFile() {
//        myFixture.copyFileToProject("Sample_after.java")
        CodeInsightTestUtil.doActionTest(RemoveHungarianAction(), "Sample.java", myFixture)
    }

}
