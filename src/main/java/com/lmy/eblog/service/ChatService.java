package com.lmy.eblog.service;



import com.lmy.eblog.im.vo.ImMess;
import com.lmy.eblog.im.vo.ImUser;

import java.util.List;

public interface ChatService {
    ImUser getCurrentUser();

    void setGroupHistoryMsg(ImMess responseMess);

    List<Object> getGroupHistoryMsg(int count);
}
