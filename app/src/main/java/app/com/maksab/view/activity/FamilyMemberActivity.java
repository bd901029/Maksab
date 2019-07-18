package app.com.maksab.view.activity;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import app.com.maksab.R;
import app.com.maksab.api.APIClient;
import app.com.maksab.api.Api;
import app.com.maksab.api.dao.CategoryHomeResponse;
import app.com.maksab.api.dao.GetFamilyMembers;
import app.com.maksab.api.dao.SuccessfulResponse;
import app.com.maksab.databinding.ActivityFamilyMembersBinding;
import app.com.maksab.listener.DialogListener;
import app.com.maksab.listener.OnItemClickListener;
import app.com.maksab.util.ProgressDialog;
import app.com.maksab.util.Utility;
import app.com.maksab.view.adapter.FamilyMemberAdapter;
import app.com.maksab.view.viewmodel.MemberIdModel;
import app.com.maksab.view.viewmodel.UserIdModel;
import retrofit2.Call;
import retrofit2.Callback;

public class FamilyMemberActivity extends AppCompatActivity {
    ActivityFamilyMembersBinding mBinding;
    Activity mcontext;
    FamilyMemberAdapter adapter;
    private String addMember = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_family_members);
        mBinding.setActivity(this);
        mcontext = this;
        initViews();
    }

    public void onClickBack() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void initViews() {
        adapter = new FamilyMemberAdapter(mcontext, new OnItemClickListener() {
            @Override
            public void onClick(int position, Object obj) {
                final GetFamilyMembers.FamMember famMember = (GetFamilyMembers.FamMember) obj;
                Utility.setDialog(FamilyMemberActivity.this, getString(R.string.alert), getString(R.string
                                .remove_favourite_message),
                        getString(R.string.no), getString(R.string.yes), new DialogListener() {
                            @Override
                            public void onNegative(DialogInterface dialog) {
                                dialog.dismiss();
                            }

                            @Override
                            public void onPositive(DialogInterface dialog) {
                                dialog.dismiss();
                                ProgressDialog.getInstance().showProgressDialog(FamilyMemberActivity.this);
                                MemberIdModel memberIdModel = new MemberIdModel();
                                memberIdModel.setLanguage(Utility.getLanguage(FamilyMemberActivity.this));
                                memberIdModel.setMemberId(famMember.getMemberId());
                                Api api = APIClient.getClient().create(Api.class);
                                final Call<SuccessfulResponse> responseCall = api.deleteFamily_member(memberIdModel);
                                responseCall.enqueue(new Callback<SuccessfulResponse>() {
                                    @Override
                                    public void onResponse(Call<SuccessfulResponse> call, retrofit2.Response<SuccessfulResponse>
                                            response) {
                                        ProgressDialog.getInstance().dismissDialog();
                                        Utility.showToast(FamilyMemberActivity.this, response.body().getMessage() + "");
                                        //famMemberArrayList.remove(famMember);
                                        getFamilyMember();
                                    }

                                    @Override
                                    public void onFailure(Call<SuccessfulResponse> call, Throwable t) {
                                        ProgressDialog.getInstance().dismissDialog();
                                        Log.e("", "onFailure: " + t.getLocalizedMessage());
                                    }
                                });
                            }
                        });
            }
        });
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mBinding.familyTreeRv.setLayoutManager(manager);
        mBinding.familyTreeRv.setAdapter(adapter);
        mBinding.addMemberIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FamilyMemberActivity.this, AddMemberActivity.class);
                startActivityForResult(intent, 265);
            }
        });
        getFamilyMember();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 265) {
            if (resultCode == RESULT_OK) {
                getFamilyMember();
            }
        }
    }

    private void getFamilyMember() {
        ProgressDialog.getInstance().showProgressDialog(mcontext);
        UserIdModel model = new UserIdModel();
        model.setUserId(Utility.getUserId(mcontext));
        model.setLanguage(Utility.getLanguage(mcontext));
        Api api = APIClient.getClient().create(Api.class);
        final Call<GetFamilyMembers> responseCall = api.getFamilyMember(model);
        responseCall.enqueue(new Callback<GetFamilyMembers>() {
            @Override
            public void onResponse(Call<GetFamilyMembers> call, retrofit2.Response<GetFamilyMembers> response) {
                ProgressDialog.getInstance().dismissDialog();
                GetFamilyMembers amount = response.body();
                adapter.setList(amount.getFamMembers());
                addMember = amount.getFamMembers().size() + "";
                mBinding.numberOfMember.setText(amount.getFamMembers().size() + "/" + Utility.getMaxDiviceCount
                        (FamilyMemberActivity.this));
                /* if (!addMember.equalsIgnoreCase(Utility.getMaxDiviceCount(FamilyMemberActivity.this))) {
                }*/
                if (Integer.parseInt(addMember) >= Integer.parseInt(Utility.getMaxDiviceCount(FamilyMemberActivity.this))) {
                    mBinding.addMemberIv.setVisibility(View.INVISIBLE);
                }else {
                    mBinding.addMemberIv.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GetFamilyMembers> call, Throwable t) {
                ProgressDialog.getInstance().dismissDialog();
                Utility.showToast(FamilyMemberActivity.this, t + "");
            }
        });
    }
}
