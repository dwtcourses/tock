/*
 * Copyright (C) 2017/2020 e-voyageurs technologies
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ai.tock.bot.connector.whatsapp

import ai.tock.bot.connector.whatsapp.model.common.WhatsAppTextBody
import ai.tock.bot.connector.whatsapp.model.webhook.WhatsAppTextMessage
import ai.tock.bot.engine.action.SendSentence
import ai.tock.bot.engine.user.PlayerId
import ai.tock.bot.engine.user.PlayerType.bot
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class WebhookActionConverterTest {

    @Test
    fun `check user id is encrypted and decrypted`() {
        val from = "my phone number"
        val message = WhatsAppTextMessage(WhatsAppTextBody("text"), "id", from, "a")
        val s = WebhookActionConverter.toEvent(message, "appId", mockk()) as SendSentence

        assertNotEquals(from, s.playerId.id)

        val output = SendActionConverter.toBotMessage(
            SendSentence(
                PlayerId("botId", bot),
                "appId",
                PlayerId(s.playerId.id),
                "Hey"
            )
        )

        assertEquals(from, output?.to)
    }
}
