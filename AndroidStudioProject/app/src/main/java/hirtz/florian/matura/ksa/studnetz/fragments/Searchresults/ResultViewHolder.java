package hirtz.florian.matura.ksa.studnetz.fragments.Searchresults;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import hirtz.florian.matura.ksa.studnetz.R;
import hirtz.florian.matura.ksa.studnetz.models.UserModel;


/**
 * Created by Florian Hirtz on 12.08.2018.
 */

class ResultViewHolder extends RecyclerView.ViewHolder{

    private UserModel model;
    private View view;

    public ResultViewHolder(View view) {
        super(view);
        this.view = view;
    }
    
    public void validate(UserModel model) {
        this.model = model;
        TextView name_tv = view.findViewById(R.id.result_name_textview);
        TextView school_tv = view.findViewById(R.id.result_school_textview);

        ImageView pb_imageview = view.findViewById(R.id.result_profilepicture_imageview);

        ImageView chemistry_medal = view.findViewById(R.id.result_chemistrymedal_imageview);
        ImageView french_medal = view.findViewById(R.id.result_frenchmedal_imageview);
        ImageView spanish_medal = view.findViewById(R.id.result_spanishmedal_imageview);
        ImageView maths_medal = view.findViewById(R.id.result_mathsmedal_imageview);
        ImageView physics_medal = view.findViewById(R.id.result_physicsmedal_imageview);
        ImageView biology_medal = view.findViewById(R.id.result_biologymedal_imageview);
        ImageView music_medal = view.findViewById(R.id.result_musicmedal_imageview);
        ImageView german_medal = view.findViewById(R.id.result_germanmedal_imageview);
        ImageView english_medal = view.findViewById(R.id.result_englishmedal_imageview);

        if(!model.isMaths()) { maths_medal.setVisibility(View.GONE); }
        if(!model.isSpanish()) { spanish_medal.setVisibility(View.GONE); }
        if(!model.isFrench()) { french_medal.setVisibility(View.GONE); }
        if(!model.isBiology()) { biology_medal.setVisibility(View.GONE); }
        if(!model.isPhysics()) { physics_medal.setVisibility(View.GONE); }
        if(!model.isEnglish()) { english_medal.setVisibility(View.GONE); }
        if(!model.isGerman()) { german_medal.setVisibility(View.GONE); }
        if(!model.isMusic()) { music_medal.setVisibility(View.GONE); }
        if(!model.isChemistry()) { chemistry_medal.setVisibility(View.GONE); }

        name_tv.setText(model.getFirstname() + " " + model.getName());
        school_tv.setText(model.getSchool() + model.getStringGrade());
    }

    public void setModel(UserModel model) {
        this.model = model;
    }
    
    public UserModel getModel(){
        return this.model;
    }
    
    public void setView(View view){
        this.view = view;
    }
    
    public View getView() {
        return this.view;
    }
}
