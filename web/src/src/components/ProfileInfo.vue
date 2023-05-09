<template>
    <div class="sidebar-group">
        <div id="contact-information" class="sidebar active">
            <header style="padding-right: 10px;">
                <span>Profile</span>
                <span style="margin-left: 30px;">
                    <el-button type="primary" v-if="user.id === groupInfo.userId && props.profileType === 2" @click="showInviteUser = true">Invite</el-button>
                </span>
                <span style="">
                    <el-button type="warning" v-if="user.id === groupInfo.userId && props.profileType === 2" @click="showEditGroupInfo = true">Edit</el-button>
                </span>
                <span>
                    <el-button type="danger" @click="closeProfileInfo">Close</el-button>
                </span>
            </header>
            <div class="sidebar-body">
                <div class="pl-4 pr-4">
                    <div class="text-center">
                        <figure class="avatar avatar-xl mb-4" v-if="props.profileType === 1">
                            <img :src="computeFileUrl(receiverUser.headPic)" class="rounded-circle" alt="image">
                        </figure>
                        <figure class="avatar avatar-xl mb-4" v-if="props.profileType === 2">
                            <img v-if="groupInfo.groupPic !== ''" :src="computeFileUrl(groupInfo.groupPic)" class="rounded-circle" alt="image">
                            <span v-else class="avatar-title bg-warning bg-success rounded-circle">
                                <i class="fa fa-users"></i>
                            </span>
                        </figure>
                        <h5 class="mb-1" v-if="props.profileType === 1">{{receiverUser.username}}</h5>
                        <h5 class="mb-1" v-if="props.profileType === 2">{{groupInfo.name}}</h5>
                        <ul class="nav nav-tabs justify-content-center mt-5" id="myTab" role="tablist">
                            <li class="nav-item">
                                <a class="nav-link active" v-if="props.profileType === 1" href="javascript:void(0);"
                                   aria-controls="home" aria-selected="true">Introduction</a>
                                <a class="nav-link active" v-if="props.profileType === 2" href="javascript:void(0);"
                                   aria-controls="home" aria-selected="true">Introduction</a>
                            </li>
                        </ul>
                    </div>
                    <div class="tab-content" id="myTabContent">
                        <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                            <p class="text-muted" v-if="props.profileType === 1">{{receiverUser.info}}</p>
                            <p class="text-muted" v-if="props.profileType === 2">{{groupInfo.info}}</p>
                            <div class="mt-4 mb-4" v-if="props.profileType === 1">
                                <h6>UID</h6>
                                <p class="text-muted">{{receiverUser.id}}</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 1">
                                <h6>Phone Number</h6>
                                <p class="text-muted">{{receiverUser.phone}}</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 2">
                                <h6>Creator</h6>
                                <p class="text-muted">{{groupInfo.username}}</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 1">
                                <h6>Sex</h6>
                                <p class="text-muted" v-if="receiverUser.sex === 1">Male</p>
                                <p class="text-muted" v-if="receiverUser.sex === 2">Female</p>
                                <p class="text-muted" v-if="receiverUser.sex === 3">Unknown</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 1">
                                <h6>City</h6>
                                <p class="text-muted">{{receiverUser.city}}</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 2">
                                <h6>Create Time</h6>
                                <p class="text-muted">{{groupInfo.createTime}}</p>
                            </div>
                            <div class="mt-4 mb-4" v-if="props.profileType === 2">
                                <h6>Group Chat Member</h6>
                                <p class="text-muted">
                                    <template v-for="(item,index) in groupInfo.groupItemList" :key="index">
                                        <el-popover v-model:visible="visible" placement="bottom">
                                            <div style="text-align: center;">
                                                <el-button size="small" type="primary" text @click="openShowPersonInfo(item.userDTO)">View</el-button>
                                                <el-button v-if="user.id !== item.userDTO.id && groupInfo.userId === user.id" size="small" type="danger" text @click="exitGroup(groupInfo.id, item.userDTO.id)">Kick</el-button>
                                            </div>
                                            <template #reference>
                                                 <span :title="item.userDTO.username">
                                                    <el-avatar :src="computeFileUrl(item.userDTO.headPic)"></el-avatar>
                                                </span>
                                            </template>
                                        </el-popover>

                                    </template>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <el-dialog v-model="showInviteUser" title="Invite User" :close-on-click-modal="false">
            <el-form :model="inviteUserData">
                <el-form-item label="UID" label-width="100px">
                    <el-input v-model="inviteUserData.id" placeholder="Enter UID" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="showInviteUser = false">Cancel</el-button>
                <el-button type="primary" @click="inviteGroupUser">Confirm</el-button>
              </span>
            </template>
        </el-dialog>


        <el-dialog v-model="showPersonInfo" title="Info" :close-on-click-modal="false">
            <el-form :model="viewUserInfo">
                <el-form-item label="UID" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.id}}</span>
                </el-form-item>
                <el-form-item label="User Name" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.username}}</span>
                </el-form-item>
                <el-form-item label="Profile Photo" label-width="120px">
                    <img :src="computeFileUrl(viewUserInfo.headPic)" id="person-photo-view" style="width:100px; height:70px;" />
                </el-form-item>
                <el-form-item label="Phone Number" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.phone}}</span>
                </el-form-item>
                <el-form-item label="City" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.city}}</span>
                </el-form-item>
                <el-form-item label="Sex" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.sex}}</span>
                </el-form-item>
                <el-form-item label="Introduction" label-width="120px">
                    <span style="margin-bottom: 7px">{{viewUserInfo.info}}</span>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button type="primary" @click="showPersonInfo = false">Confirm</el-button>
              </span>
            </template>
        </el-dialog>

        <el-dialog v-model="showEditGroupInfo" title="Edit Group Info" :close-on-click-modal="false">
            <el-form :model="saveGroupInfo">
                <el-form-item label="Group Chat Name" label-width="100px">
                    <el-input v-model="saveGroupInfo.name" placeholder="Enter Chat Group Name" autocomplete="off"></el-input>
                </el-form-item>
                <el-form-item label="Group Pic" label-width="100px">
                    <input type="file" ref="photo" id="photo" style="display:none;" @change="uploadPhoto">
                    <figure class="avatar avatar-xl mb-4" style="width: 5.6rem; height: 5.6rem">
                        <img v-if="saveGroupInfo.groupPic !== ''" :src="computeFileUrl(saveGroupInfo.groupPic)" id="photo-view" style="width:100px; height:100px;" />
                        <span v-else class="avatar-title bg-warning bg-success rounded-circle">
                            <i class="fa fa-users"></i>
                        </span>
                    </figure>
                    <el-button type="danger" @click="openUploadPhoto" style="vertical-align:middle;float:none;margin-left:20px;">Photo Upload</el-button>
                </el-form-item>
                <el-form-item label="Introduction" label-width="100px">
                    <el-input v-model="saveGroupInfo.info" type="textarea" :row="5" placeholder="Enter Group Information" autocomplete="off"></el-input>
                </el-form-item>
            </el-form>
            <template #footer>
              <span class="dialog-footer">
                <el-button @click="showEditGroupInfo = false">Cancel</el-button>
                <el-button type="primary" @click="editGroupInfo">Confirm</el-button>
              </span>
            </template>
        </el-dialog>
    </div>
</template>

<script lang="ts">
    import {defineComponent, onMounted, reactive, ref, watch} from "vue";
    import axios from "axios";
    import Tool from "@/utils/Tool";
    import Message from "@/utils/Message";
    import EIcon from '../components/EIcon.vue';
    import {useRouter} from "vue-router";
    export default defineComponent({
        name: "ProfileInfo",
        props: {
            EIcon,
            profileType: Number,
            receiver: String,
            groupId: String,
            chatId: String
        },
        computed: {
            computeFileUrl() {
                return function(url: string) {
                    return process.env.VUE_APP_SERVER + "/photo/view?filename=" + url;
                }
            }
        },
        setup(props, context) {
            const router = useRouter();
            let user = reactive({id: "", username: ""});
            let showInviteUser = ref(false);
            let inviteUserData = reactive({id: ""});

            // 关闭个人信息页面
            function closeProfileInfo() {
                context.emit("closeProfileInfo")
            }

            watch(props,(newProps, oldProps) => {
                if(props.profileType === 1) {
                    // 用户信息
                    getReceiverUserInfo();
                } else if(props.profileType === 2) {
                    // 群聊信息
                    getReceiverGroupInfo();
                }
            });

            let showEditGroupInfo = ref(false);

            onMounted(() => {
                // 动态加载js文件
                Tool.loadJs();
                // 验证是否登录
                const token = Tool.getLoginUser();
                axios.post(process.env.VUE_APP_SERVER + "/web/user/check_login", {token: token}).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        const data = resp.data;
                        user.id = data.id;
                        user.username = data.username;
                        if(props.profileType === 1) {
                            // 用户信息
                            getReceiverUserInfo();
                        } else if(props.profileType === 2) {
                            // 群聊信息
                            getReceiverGroupInfo();
                        }

                    }else {
                        router.push({
                            path:"/"
                        });
                    }
                });
            });

            let receiverUser = reactive({id: "", username: "", info: "", sex: "", phone: "", city: "", headPic: ""});
            // 获取用户信息资料
            function getReceiverUserInfo() {
                axios.post(process.env.VUE_APP_SERVER + "/web/user/get", {id: props.receiver}).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        const data = resp.data;
                        receiverUser.id = data.id;
                        receiverUser.username = data.username;
                        receiverUser.phone = data.phone;
                        receiverUser.city = data.city;
                        receiverUser.headPic = data.headPic;
                        receiverUser.info = data.info;
                        receiverUser.sex = data.sex;
                    }
                });
            }

            let groupInfo = reactive({id: "", name: "", chatId: "", userId: "", info: "", groupPic: "", username: "", createTime: "", groupItemList: []});
            // 获取群聊信息资料
            function getReceiverGroupInfo() {
                axios.post(process.env.VUE_APP_SERVER + "/web/group/get", {id: props.groupId}).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        const data = resp.data;
                        groupInfo.id = data.id;
                        groupInfo.name = data.name;
                        groupInfo.info = data.info;
                        groupInfo.groupPic = data.groupPic;
                        groupInfo.userId = data.userId;
                        groupInfo.chatId = data.chatId;
                        groupInfo.username = data.userDTO.username;
                        groupInfo.createTime = data.createTime;
                        groupInfo.groupItemList = data.groupItemDTOList;
                        // 群聊更新信息赋值
                        saveGroupInfo.id = data.id;
                        saveGroupInfo.name = data.name;
                        saveGroupInfo.info = data.info;
                        saveGroupInfo.groupPic = data.groupPic;
                    }
                });
            }

            // 邀请用户加入群聊
            function inviteGroupUser() {
                axios.post(process.env.VUE_APP_SERVER + "/web/group/invite", {id: groupInfo.id, userId: inviteUserData.id}).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        Message.alertMsg("Invite Successfully！", "success");
                        getReceiverGroupInfo();
                        if(props.chatId === groupInfo.chatId) {
                            context.emit("getChatGroup", resp.data, props.chatId);
                        }
                        showInviteUser.value = false;
                    } else {
                        Message.alertMsg(resp.msg, "error");
                    }
                });
            }

            let saveGroupInfo = reactive({id: "", name: "", groupPic: "", info: "", userId: ""});
            // 修改群聊信息
            function editGroupInfo() {
                saveGroupInfo.userId = user.id;
                axios.post(process.env.VUE_APP_SERVER + "/web/group/save", saveGroupInfo).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        Message.alertMsg("Update Successfully！", "success");
                        getReceiverGroupInfo();
                        const data = resp.data;
                        if(data.chatId === props.chatId) {
                            context.emit('getChatGroup', data, props.chatId);
                        }
                        showEditGroupInfo.value = false;
                    } else {
                        Message.alertMsg(resp.msg, "error");
                    }
                });
            }

            // 打开图片上传窗口
            function openUploadPhoto() {
                (document as any).getElementById("photo").click();
            }

            // 上传图片
            const photo = ref(null);
            function uploadPhoto() {
                const photoList = (photo as any).value.files;
                if(photoList === null || photoList.length !== 1) {
                    Message.alertMsg("Select a Picture！", "error");
                    return;
                }
                const uploadPhoto = photoList[0];
                const config = {
                    headers:{'Content-Type':'multipart/form-data'}
                };
                const formData = new FormData();
                formData.append('photo', uploadPhoto);
                axios.post(process.env.VUE_APP_SERVER + "/photo/upload", formData, config).then((response)=>{
                    let resp = response.data;
                    if(resp.code === 0) {
                        Message.alertMsg(resp.msg, "success");
                        saveGroupInfo.groupPic = resp.data;
                    } else {
                        Message.alertMsg(resp.msg, "error");
                    }
                });
            }

            const showPersonInfo = ref(false);
            let viewUserInfo = reactive({id: "", username: "", headPic: "", phone: "", city: "", info: "", sex: ""});

            // 打开查看用户个人信息的窗口
            function openShowPersonInfo(item: any) {
                showPersonInfo.value = true;
                viewUserInfo.id = item.id;
                viewUserInfo.username = item.username;
                viewUserInfo.headPic = item.headPic;
                viewUserInfo.phone = item.phone;
                viewUserInfo.city = item.city;
                viewUserInfo.info = item.info;
                if(item.sex === 1) {
                    viewUserInfo.sex = "Male";
                } else if(item.sex === 2) {
                    viewUserInfo.sex = "Female";
                } else {
                    viewUserInfo.sex = "Unknown";
                }
            }


            // 踢出群聊
            function exitGroup(id: string, userId: string) {
                const confirm = Message.confirmMsg("Sure to Kick？");
                confirm.then(() => {
                    axios.post(process.env.VUE_APP_SERVER + "/web/group/exit", {id: id, userId: userId}).then((response)=>{
                        let resp = response.data;
                        if(resp.code === 0) {
                            Message.alertMsg( "Kick Successfully！", "success");
                            getReceiverGroupInfo();
                            context.emit("getChatGroup", resp.data, props.chatId);
                        }else {
                            Message.alertMsg(resp.msg, "error");
                        }
                    });
                }).catch((e) => {
                    console.log(e);
                });
            }


            return {
                closeProfileInfo,
                props,
                receiverUser,
                groupInfo,
                user,
                inviteGroupUser,
                showInviteUser,
                inviteUserData,
                showEditGroupInfo,
                editGroupInfo,
                saveGroupInfo,
                openUploadPhoto,
                uploadPhoto,
                photo,
                showPersonInfo,
                openShowPersonInfo,
                viewUserInfo,
                exitGroup
            }
        }
    });
</script>
