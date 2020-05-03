package com.gy.wyy.chat.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.gy.wyy.chat.ui.R;
import com.gy.wyy.chat.ui.entity.MessageEntity;

/**
 *
 */
public class MessageTextHolder extends MessageContentHolder {

    private TextView msgBodyText;

    public MessageTextHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void layoutVariableViews(MessageEntity entity, int position) {
        msgBodyText.setVisibility(View.VISIBLE);
        msgBodyText.setText(""+entity.getText());
        if (entity.isSelf()) {
            if (properties.getRightContentFontColor() != 0) {
                msgBodyText.setTextColor(properties.getRightContentFontColor());
            }else {
                msgBodyText.setTextColor(rootView.getResources().getColor(R.color.message_text_white));
            }
        } else {
            if (properties.getLeftContentFontColor() != 0) {
                msgBodyText.setTextColor(properties.getLeftContentFontColor());
            }else {
                msgBodyText.setTextColor(rootView.getContext().getResources().getColor(R.color.message_text_black));
            }
        }
    }

    @Override
    public int getVariableLayout() {
        return R.layout.message_item_text;
    }

    @Override
    public void initVariableViews() {
        msgBodyText = rootView.findViewById(R.id.message_item_text_tv);
    }
}
