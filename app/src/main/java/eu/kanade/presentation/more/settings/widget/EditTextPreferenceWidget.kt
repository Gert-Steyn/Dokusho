package eu.kanade.presentation.more.settings.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.launch
import mihon.icons.lucide.Lucide
import mihon.icons.lucide.icons.CircleAlert
import mihon.icons.lucide.icons.CircleX
import tachiyomi.i18n.MR
import tachiyomi.presentation.core.components.graphite.GDialog
import tachiyomi.presentation.core.components.graphite.GGhostButton
import tachiyomi.presentation.core.components.graphite.GIconButton
import tachiyomi.presentation.core.components.graphite.GPrimaryButton
import tachiyomi.presentation.core.components.graphite.graphiteTextFieldColors
import tachiyomi.presentation.core.i18n.stringResource
import tachiyomi.presentation.core.theme.GRadius

@Composable
fun EditTextPreferenceWidget(
    title: String,
    subtitle: String?,
    icon: ImageVector?,
    value: String,
    onConfirm: suspend (String) -> Boolean,
) {
    var isDialogShown by remember { mutableStateOf(false) }

    TextPreferenceWidget(
        title = title,
        subtitle = subtitle?.format(value),
        icon = icon,
        onPreferenceClick = { isDialogShown = true },
    )

    if (isDialogShown) {
        val scope = rememberCoroutineScope()
        val onDismissRequest = { isDialogShown = false }
        var textFieldValue by rememberSaveable(stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue(value))
        }
        GDialog(
            onDismissRequest = onDismissRequest,
            title = { Text(text = title) },
            text = {
                OutlinedTextField(
                    value = textFieldValue,
                    onValueChange = { textFieldValue = it },
                    trailingIcon = {
                        if (textFieldValue.text.isBlank()) {
                            Icon(imageVector = Lucide.Icons.CircleAlert, contentDescription = null)
                        } else {
                            GIconButton(
                                icon = Lucide.Icons.CircleX,
                                contentDescription = stringResource(MR.strings.action_reset),
                                onClick = { textFieldValue = TextFieldValue("") },
                            )
                        }
                    },
                    isError = textFieldValue.text.isBlank(),
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = GRadius.control,
                    colors = graphiteTextFieldColors(),
                )
            },
            confirmButton = {
                GPrimaryButton(
                    text = stringResource(MR.strings.action_ok),
                    enabled = textFieldValue.text != value && textFieldValue.text.isNotBlank(),
                    onClick = {
                        scope.launch {
                            if (onConfirm(textFieldValue.text)) {
                                onDismissRequest()
                            }
                        }
                    },
                )
            },
            dismissButton = {
                GGhostButton(
                    text = stringResource(MR.strings.action_cancel),
                    onClick = onDismissRequest,
                )
            },
        )
    }
}
