package com.sivin.adapter.section.caching;

import android.support.v7.widget.RecyclerView;
import android.view.View;


public interface SectionProvider {


   View getSection(RecyclerView recyclerView, int position);


  void invalidate();
}
