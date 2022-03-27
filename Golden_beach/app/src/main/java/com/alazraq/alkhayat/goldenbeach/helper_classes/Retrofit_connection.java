package com.alazraq.alkhayat.goldenbeach.helper_classes;

import com.alazraq.alkhayat.goldenbeach.Host_api_interface;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_complainants;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_suggestions;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_advertisements;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_just_admin;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_comments;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Sum_of_evaluations;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.User_login_items;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit_connection {

    private static final String BASE_URL ="http://goldenbeachye.com/";
    private Host_api_interface host_api_interface;
    private static Retrofit_connection retrofit_connection_instance;

    public Retrofit_connection() {

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        host_api_interface=retrofit.create(Host_api_interface.class);

    }


    public static Retrofit_connection getRetrofit_connection_instance() {
        if(null == retrofit_connection_instance){
            retrofit_connection_instance =new Retrofit_connection();

        }
        return retrofit_connection_instance;
    }

    public Call<Response_messages>addNewPost(String name_of_section,String brand_of_section,
                                         String name_of_category,String name,int session,
                                         String name_of_image,int year,String story,String trailer){

        return host_api_interface.addNewPost(name_of_section,brand_of_section,name_of_category,name,session,name_of_image,year,story,trailer);

    }


    public Call<Response_messages>updatePostsWithImage(int id,String name_of_section,String brand_of_section,
                                             String name_of_category,String name,int session,
                                             String name_of_image,int year,String story,String trailer){

        return host_api_interface.updatePostsWithImage(id,name_of_section,brand_of_section,name_of_category,name,session,name_of_image,year,story,trailer);

    }

    public Call<Response_messages>updatePostsWithOutImage(int id,String name_of_section,String brand_of_section,
                                                       String name_of_category,String name,int session,
                                                       int year,String story,String trailer){

        return host_api_interface.updatePostsWithOutImage(id,name_of_section,brand_of_section,name_of_category,name,session,year,story,trailer);

    }



    public Call<Response_messages>deletePost(int id){

        return host_api_interface.deletePost(id);
    }

    public Call<List<Items_of_advertisements>>getAllAdds(){

        return host_api_interface.getAllAdds();
    }


    public Call<Response_messages>deleteAdd(int add_id){

        return host_api_interface.deleteAdd(add_id);
    }


    public Call<Response_messages>addNewAdd(String name_of_owner,String name_of_image_of_add,
                                            String description,String date_of_add){

        return host_api_interface.addNewAdd(name_of_owner,name_of_image_of_add,description,date_of_add);
    }

    public Call<Response_messages> updateAddWithImage(int add_id, String name_of_owner, String name_of_image_of_add,
                                                      String description, String date_of_add){

        return host_api_interface.updateAddWithImage(add_id,name_of_owner,name_of_image_of_add,description,date_of_add);
    }

    public Call<Response_messages> updateAddWithOutImage(int add_id, String name_of_owner,
                                                      String description, String date_of_add){

        return host_api_interface.updateAddWithOutImage(add_id,name_of_owner,description,date_of_add);
    }


    public Call<User_login_items> getUserLoginItems(String user_name){

        return host_api_interface.userLogin(user_name);

    }

    public Call<Response_messages>userSignUp(String user_full_name,String user_email,
                                             String user_name,String user_password,
                                             String user_name_of_image){

        return host_api_interface.userSignUp(user_full_name,user_email,user_name,user_password,user_name_of_image);

    }

    public Call<List<Items_of_all_list>>getLast20Posts(){

        return host_api_interface.getLast20Posts();
    }


    public Call<List<Items_of_all_list>>getLast30OfAllHomeFragment(String name_of_section){

        return host_api_interface.getLast30OfAllHomeFragment(name_of_section);
    }

    public Call<List<Items_of_all_list>>getAll(String name_of_section, String brand_of_section, String name_of_category,int limit){

        return host_api_interface.getAll(name_of_section,brand_of_section,name_of_category,limit);
    }

    public Call<List<Items_of_all_list>>getLast20OfAll(String name_of_section, String brand_of_section, String name_of_category){

        return host_api_interface.getLast20OfAll(name_of_section,brand_of_section,name_of_category);
    }

    public Call<List<Items_of_all_list>> getAllJustByNameOfSection(String name_of_section,int limit){

        return host_api_interface.getAllJustByNameOfSection(name_of_section,limit);
    }

    public Call<List<Items_of_all_list>>getLast20OfAllJustByNameOfSection(String name_of_section){

        return host_api_interface.getLast20OfAllJustByNameOfSection(name_of_section);
    }



    public Call<List<Items_of_all_list>>getAll2(String name_of_section,int limit){

        return host_api_interface.getAll2(name_of_section,limit);
    }





    public Call<Response_messages> insertComment(int id_of_user,int id_of_all ,String comment){

        return host_api_interface.insertComment(id_of_user,id_of_all,comment);
    }

     public Call<List<Items_of_comments>>getAllComments(int id_of_all , int limit){

        return host_api_interface.getAllComments(id_of_all,limit);
    }

    public Call<Sum_of_evaluations>getSumOfEvaluationValues(int id_of_all){

        return host_api_interface.getSumOfEvaluationValues(id_of_all);
    }

    public Call <Response_messages>insertAndUpdateEvaluation(int id_of_user,int id_of_all,float value_of_evaluation){

        return host_api_interface.insertAndUpdateEvaluation(id_of_user,id_of_all,value_of_evaluation);
    }

    public Call<List<Items_of_all_list>>searchByName(String name,int limit){

        return host_api_interface.searchByName(name,limit);
    }

    public Call<List<Items_of_all_list>>searchByNameWithDetails(String name,String name_of_section,String brand_of_section,String name_of_category,int limit){

        return host_api_interface.searchByNameWithDetails(name,name_of_section,brand_of_section,name_of_category,limit);
    }

    public Call<List<Items_of_all_list>>searchByYear(int year,int limit){

        return host_api_interface.searchByYear(year,limit);
    }

    public Call<List<Items_of_all_list>>searchByYearWithDetails(int year,String name_of_section,String brand_of_section,String name_of_category,int limit){

        return host_api_interface.searchByYearWithDetails(year,name_of_section,brand_of_section,name_of_category,limit);
    }

/*
    public Call<List<Items_of_all_list>>searchOfAll(String name_of_section,String brand_of_section,String name_of_category,String name,int year,float evaluation,String search_method,int limit){

        return host_api_interface.searchByName(name_of_section,brand_of_section,name_of_category,name,year,evaluation,search_method,limit);
    }
    public Call<Response_messages>searchOfAllByYear(String name_of_section,String brand_of_section,String name_of_category,int year,String search_method){

        return host_api_interface.searchOfAllByYear(name_of_section,brand_of_section,name_of_category,year,search_method);
    }


    public Call<Response_messages>searchOfAllByEvaluation(String name_of_section,String brand_of_section,String name_of_category,Float evaluation,String search_method){

        return host_api_interface.searchOfAllByEvaluation(name_of_section,brand_of_section,name_of_category,evaluation,search_method);
    }

 */

    public Call<List<Items_of_chatting_just_admin>>getJustAdminForChatting(){

        return host_api_interface.getJustAdminForChatting();
    }

    public Call<List<Items_of_chatting_just_admin>>getAllUsersForChatting(){

        return host_api_interface.getAllUsersForChatting();
    }

    public Call<Response_messages> insertSuggestion(int id_of_user, String suggestion,String suggestion_date){

        return host_api_interface.insertSuggestion(id_of_user,suggestion,suggestion_date);
    }

    public Call<List<Items_of_base_suggestions>> getAllSuggestions(int limit){

        return host_api_interface.getAllSuggestions(limit);
    }


    public Call<Response_messages> insertComplainant(int id_of_user,String complainant,String complainant_date){

        return host_api_interface.insertComplainant(id_of_user,complainant,complainant_date);
    }

    public Call<List<Items_of_base_complainants>> getAllComplainants(int limit){

        return host_api_interface.getAllComplainants(limit);
    }



}
