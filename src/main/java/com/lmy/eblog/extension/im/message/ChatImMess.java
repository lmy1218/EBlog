package com.lmy.eblog.extension.im.message;


import com.lmy.eblog.extension.im.vo.ImTo;
import com.lmy.eblog.extension.im.vo.ImUser;
import lombok.Data;

@Data
public class ChatImMess {

    private ImUser mine;
    private ImTo to;

}
