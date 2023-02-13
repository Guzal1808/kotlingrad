package ru.sber.kotlinschool.telegram.stepActions

import org.springframework.stereotype.Component
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import ru.sber.kotlinschool.telegram.entity.Step

@Component("DATE_TIME_CALC")
class DateTimeCalculationAction : Action() {

    override fun execute(currentStep: Step, chatId: String): SendMessage {
        val responseMessage = SendMessage(chatId, currentStep.messageForUser)
        responseMessage.enableMarkdown(true)

        val range = if (currentStep.configParams.isNotEmpty()) toRange(currentStep.configParams[0])
        else toRange("9-18")

        val inlineKeyboardMarkup = InlineKeyboardMarkup() //Создаем объект разметки клавиатуры
        val keyboardButtonsRow1: MutableList<InlineKeyboardButton> = ArrayList()
        val rowList: MutableList<List<InlineKeyboardButton>> = ArrayList() //Создаём ряд

        for (i in range) {
            val inlineKeyboardButton1 = InlineKeyboardButton()
            inlineKeyboardButton1.text = "${i}:00"
            inlineKeyboardButton1.callbackData = "${i}:00"
            keyboardButtonsRow1.add(inlineKeyboardButton1)
        }

        rowList.add(keyboardButtonsRow1)

        inlineKeyboardMarkup.keyboard = rowList

        responseMessage.replyMarkup = inlineKeyboardMarkup

        return responseMessage
    }

    fun toRange(str: String): IntRange = str
        .split("-")
        .let { (a, b) -> a.toInt()..b.toInt() }
}