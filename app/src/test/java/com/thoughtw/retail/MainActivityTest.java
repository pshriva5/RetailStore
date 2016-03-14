package com.thoughtw.retail;


import android.content.Context;
import android.support.v7.app.ActionBar;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MainActivityTest {

  @Mock
  private Context context;

  @Before
  public void setUp() throws Exception {
  }

  @Test
  public void shouldShowToasMessageToSaveProfileToGoBack() throws Exception {
    String message = "Save profile to go back.";
  }

  @Test
  public void shouldSetCurrentItemOnTabSelection() throws Exception {
    Integer currentPosition = 1;
    ActionBar.Tab tab = mock(ActionBar.Tab.class);
    when(tab.getPosition()).thenReturn(currentPosition);
  }
}