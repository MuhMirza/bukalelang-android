package id.clorus.bukalelang.presentation.ui.select_category;

import id.clorus.bukalelang.data.entity.response.categories.Category;

/**
 * Created by mirza on 25/05/17.
 */

public interface CategorySelectedView {

    void onCategorySelected(Category category,int position);
}
