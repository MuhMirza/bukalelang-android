package id.clorus.bukalelang.presentation.ui.select_category;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import butterknife.BindView;
import butterknife.Unbinder;
import id.clorus.bukalelang.R;
import id.clorus.bukalelang.data.entity.response.categories.Category;
import id.clorus.bukalelang.presentation.ui.base.DefaultActivity;
import id.clorus.bukalelang.presentation.utils.AppPreference;

/**
 * Created by mirza on 25/05/17.
 */

public class SelectCategoryActivity extends DefaultActivity {


    private int SELECT_CATEGORY_CODE = 212;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_category);

        changeView(R.id.container, new SelectCategoryFragment());

    }

    public void onCategorySelected(Category category){
        Intent intent=new Intent();
        intent.putExtra("categoryName",category.getName());
        intent.putExtra("categoryId",category.getId());
        intent.putExtra("categoryUrl",category.getUrl());
        setResult(SELECT_CATEGORY_CODE,intent);
        finish();//finishing activity
    }
}
