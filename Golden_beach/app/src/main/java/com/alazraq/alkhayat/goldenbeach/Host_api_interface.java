package com.alazraq.alkhayat.goldenbeach;

import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_advertisements;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_chatting_just_admin;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_comments;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Response_messages;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_complainants;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_base_suggestions;
import com.alazraq.alkhayat.goldenbeach.lists_items.Items_of_all_list;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.Sum_of_evaluations;
import com.alazraq.alkhayat.goldenbeach.retrofit_items.User_login_items;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Host_api_interface {

    @FormUrlEncoded
    @POST("add_new_post.php")
    public Call<Response_messages>addNewPost(
                                        @Field("name_of_section") String name_of_section,
                                        @Field("brand_of_section") String brand_of_section,
                                        @Field("name_of_category") String name_of_category,
                                        @Field("name") String name,
                                        @Field("session") int session,
                                        @Field("name_of_image") String name_of_image,
                                        @Field("year") int year,
                                        @Field("story") String story,
                                        @Field("trailer") String trailer);


    @FormUrlEncoded
    @POST("update_posts_with_image.php")
    public Call<Response_messages> updatePostsWithImage(@Field("id") int id,
                                                        @Field("name_of_section") String name_of_section,
                                                        @Field("brand_of_section") String brand_of_section,
                                                        @Field("name_of_category") String name_of_category,
                                                        @Field("name") String name,
                                                        @Field("session") int session,
                                                        @Field("name_of_image") String name_of_image,
                                                        @Field("year") int year,
                                                        @Field("story") String story,
                                                        @Field("trailer") String trailer);



    @FormUrlEncoded
    @POST("update_posts_with_out_image.php")
    public Call<Response_messages> updatePostsWithOutImage(@Field("id") int id,
                                                           @Field("name_of_section") String name_of_section,
                                                           @Field("brand_of_section") String brand_of_section,
                                                           @Field("name_of_category") String name_of_category,
                                                           @Field("name") String name,
                                                           @Field("session") int session,
                                                           @Field("year") int year,
                                                           @Field("story") String story,
                                                           @Field("trailer") String trailer);


    @FormUrlEncoded
    @POST("delete_post.php")
    public Call<Response_messages>deletePost(@Field("id") int id);


    @FormUrlEncoded
    @POST("add_new_add.php")
    public Call<Response_messages>addNewAdd(@Field("name_of_owner") String name_of_owner,
                                                @Field("name_of_image_of_add") String name_of_image_of_add,
                                                @Field("description") String description,
                                                @Field("date_of_add") String date_of_add);



    @FormUrlEncoded
    @POST("update_adds_with_image.php")
    public Call<Response_messages> updateAddWithImage(@Field("add_id") int add_id,
                                                      @Field("name_of_owner") String name_of_owner,
                                                      @Field("name_of_image_of_add") String name_of_image_of_add,
                                                      @Field("description") String description,
                                                      @Field("date_of_add") String date_of_add);


    @FormUrlEncoded
    @POST("update_adds_with_out_image.php")
    public Call<Response_messages> updateAddWithOutImage(@Field("add_id") int add_id,
                                                         @Field("name_of_owner") String name_of_owner,
                                                         @Field("description") String description,
                                                         @Field("date_of_add") String date_of_add);


    @FormUrlEncoded
    @POST("delete_add.php")
    public Call<Response_messages>deleteAdd(@Field("add_id") int add_id);


    @GET("users_login.php")
    public Call<User_login_items>userLogin(@Query("user_name") String user_name);


    @FormUrlEncoded
    @POST("add_new_user.php")
    public Call<Response_messages>userSignUp(@Field("user_full_name") String user_full_name,
                                                @Field("user_email") String user_email,
                                                @Field("user_name") String user_name,
                                                @Field("user_password") String user_password,
                                                @Field("user_name_of_image") String user_name_of_image);



    @GET("get_all_adds.php")
    public Call<List<Items_of_advertisements>>getAllAdds();

    @GET("get_last_20_posts.php")
    public Call<List<Items_of_all_list>>getLast20Posts();

    @GET("get_last_30_of_all_home_fragment.php")
    public Call<List<Items_of_all_list>>getLast30OfAllHomeFragment(@Query("name_of_section") String name_of_section);





    @FormUrlEncoded
    @POST("insert_comment.php")
    public Call<Response_messages>insertComment(@Field("id_of_user") int id_of_user,
                                                @Field("id_of_all") int id_of_all,
                                                @Field("comment") String comment);



    @GET("get_all_comments.php")
    public Call<List<Items_of_comments>>getAllComments(@Query("id_of_all") int id_of_all,
                                                       @Query("limit") int limit);

    @GET("get_sum_of_evaluation_values.php")
    public Call<Sum_of_evaluations>getSumOfEvaluationValues(@Query("id_of_all") int id_of_all);


    @FormUrlEncoded
    @POST("insert_and_update_evaluation.php")
    public Call <Response_messages>insertAndUpdateEvaluation(@Field("id_of_user")int id_of_user,
                                                             @Field("id_of_all") int id_of_all,
                                                             @Field("value_of_evaluation")float value_of_evaluation);


    @GET("search_by_name.php")
    public Call<List<Items_of_all_list>>searchByName(@Query("name") String name,
                                                     @Query("limit") int limit);

    @GET("search_by_name_with_details.php")
    public Call<List<Items_of_all_list>>searchByNameWithDetails(@Query("name") String name,
                                                                @Query("name_of_section")String name_of_section,
                                                                @Query("brand_of_section") String brand_of_section,
                                                                @Query("name_of_category")String name_of_category,
                                                                @Query("limit") int limit);

    @GET("search_by_year.php")
    public Call<List<Items_of_all_list>>searchByYear(@Query("year") int year,
                                                     @Query("limit") int limit);

    @GET("search_by_year_with_details.php")
    public Call<List<Items_of_all_list>>searchByYearWithDetails(@Query("year") int year,
                                                                @Query("name_of_section")String name_of_section,
                                                                @Query("brand_of_section") String brand_of_section,
                                                                @Query("name_of_category")String name_of_category,
                                                                @Query("limit") int limit);

    /*
    @FormUrlEncoded
    @POST("add_new_post.php")
    public Call<Response_messages>searchOfAllByYear(@Field("name_of_section") String name_of_section,
                                                    @Field("brand_of_section") String brand_of_section,
                                                    @Field("name_of_category") String name_of_category,
                                                    @Field("year") int year,
                                                    @Field("search_method") String search_method);
    @FormUrlEncoded
    @POST("add_new_post.php")
    public Call<Response_messages>searchOfAllByEvaluation(@Field("name_of_section") String name_of_section,
                                                            @Field("brand_of_section") String brand_of_section,
                                                            @Field("name_of_category") String name_of_category,
                                                            @Field("evaluation") float evaluation,
                                                            @Field("search_method") String search_method);


 */

    @GET("get_just_admin_for_chatting.php")
    public Call<List<Items_of_chatting_just_admin>>getJustAdminForChatting();

    @GET("get_all_users_for_chatting.php")
    public Call<List<Items_of_chatting_just_admin>>getAllUsersForChatting();

    @FormUrlEncoded
    @POST("insert_suggestion.php")
    public Call<Response_messages>insertSuggestion(@Field("id_of_user") int id_of_user,
                                                   @Field("suggestion") String suggestion,
                                                    @Field("suggestion_date") String suggestion_date);

    @GET("get_all_suggestions.php")
    public Call<List<Items_of_base_suggestions>>getAllSuggestions(@Query("limit") int limit);


    @FormUrlEncoded
    @POST("insert_complainant.php")
    public Call<Response_messages>insertComplainant(@Field("id_of_user") int id_of_user,
                                                    @Field("complainant") String complainant,
                                                    @Field("complainant_date") String complainant_date);


    @GET("get_all_complainants.php")
    public Call<List<Items_of_base_complainants>>getAllComplainants(@Query("limit") int limit);




    @GET("get_all.php")
    public Call<List<Items_of_all_list>>getAll(@Query("name_of_section") String name_of_section,
                                               @Query("brand_of_section") String brand_of_section,
                                               @Query("name_of_category") String name_of_category,
                                               @Query("limit") int limit);


    @GET("getLast20OfAll.php")
    public Call<List<Items_of_all_list>>getLast20OfAll(@Query("name_of_section") String name_of_section,
                                                       @Query("brand_of_section") String brand_of_section,
                                                       @Query("name_of_category") String name_of_category);


    @GET("get_all_just_by_name_of_section.php")
    public Call<List<Items_of_all_list>> getAllJustByNameOfSection(@Query("name_of_section") String name_of_section,
                                                                   @Query("limit") int limit);


    @GET("getLast20OfAllJustByNameOfSection.php")
    public Call<List<Items_of_all_list>>getLast20OfAllJustByNameOfSection(@Query("name_of_section") String name_of_section);





    @GET("alltesting.php")
    public Call<List<Items_of_all_list>>getAll2(@Query("name_of_section") String name_of_section,
                                                @Query("limit") int limit);






}
