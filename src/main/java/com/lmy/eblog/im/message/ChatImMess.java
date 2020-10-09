package com.lmy.eblog.im.message;


import com.lmy.eblog.im.vo.ImTo;
import com.lmy.eblog.im.vo.ImUser;
import lombok.Data;

@Data
public class ChatImMess {

    private ImUser mine;
    private ImTo to;

}
