package tachiyomi.presentation.core.components.graphite

import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import tachiyomi.presentation.core.theme.Graphite

@Composable
fun graphiteCheckboxColors(): CheckboxColors = CheckboxDefaults.colors(
    checkedCheckmarkColor = Graphite.colors.onAccent,
    uncheckedCheckmarkColor = Color.Transparent,
    checkedBoxColor = Graphite.colors.accentFill,
    uncheckedBoxColor = Color.Transparent,
    disabledCheckedBoxColor = Graphite.colors.hover,
    disabledUncheckedBoxColor = Color.Transparent,
    disabledIndeterminateBoxColor = Graphite.colors.hover,
    checkedBorderColor = Graphite.colors.accentFill,
    uncheckedBorderColor = Graphite.colors.border,
    disabledBorderColor = Graphite.colors.borderSubtle,
    disabledUncheckedBorderColor = Graphite.colors.borderSubtle,
    disabledIndeterminateBorderColor = Graphite.colors.borderSubtle,
)

@Composable
fun graphiteRadioColors(): RadioButtonColors = RadioButtonDefaults.colors(
    selectedColor = Graphite.colors.accentFill,
    unselectedColor = Graphite.colors.border,
    disabledSelectedColor = Graphite.colors.textDisabled,
    disabledUnselectedColor = Graphite.colors.borderSubtle,
)

@Composable
fun graphiteSwitchColors(): SwitchColors = SwitchDefaults.colors(
    checkedThumbColor = Graphite.colors.onAccent,
    checkedTrackColor = Graphite.colors.accentFill,
    checkedBorderColor = Graphite.colors.accentFill,
    uncheckedThumbColor = Graphite.colors.textSecondary,
    uncheckedTrackColor = Graphite.colors.border,
    uncheckedBorderColor = Graphite.colors.border,
    disabledCheckedThumbColor = Graphite.colors.canvas,
    disabledCheckedTrackColor = Graphite.colors.hover,
    disabledCheckedBorderColor = Graphite.colors.hover,
    disabledUncheckedThumbColor = Graphite.colors.textDisabled,
    disabledUncheckedTrackColor = Graphite.colors.hover,
    disabledUncheckedBorderColor = Graphite.colors.hover,
)

@Composable
fun graphiteSliderColors(): SliderColors = SliderDefaults.colors(
    thumbColor = Graphite.colors.accentFill,
    activeTrackColor = Graphite.colors.accentFill,
    inactiveTrackColor = Graphite.colors.hover,
    activeTickColor = Color.Transparent,
    inactiveTickColor = Color.Transparent,
    disabledThumbColor = Graphite.colors.textDisabled,
    disabledActiveTrackColor = Graphite.colors.textDisabled,
    disabledActiveTickColor = Color.Transparent,
    disabledInactiveTrackColor = Graphite.colors.hover,
    disabledInactiveTickColor = Color.Transparent,
)

@Composable
fun graphiteTextFieldColors(): TextFieldColors = OutlinedTextFieldDefaults.colors(
    focusedTextColor = Graphite.colors.text,
    unfocusedTextColor = Graphite.colors.text,
    disabledTextColor = Graphite.colors.textDisabled,
    errorTextColor = Graphite.colors.text,
    focusedContainerColor = Graphite.colors.canvas,
    unfocusedContainerColor = Graphite.colors.canvas,
    disabledContainerColor = Graphite.colors.hover,
    errorContainerColor = Graphite.colors.canvas,
    cursorColor = Graphite.colors.accent,
    errorCursorColor = Graphite.colors.danger,
    focusedBorderColor = Graphite.colors.accent,
    unfocusedBorderColor = Graphite.colors.border,
    disabledBorderColor = Color.Transparent,
    errorBorderColor = Graphite.colors.danger,
    focusedLabelColor = Graphite.colors.textSecondary,
    unfocusedLabelColor = Graphite.colors.textMuted,
    disabledLabelColor = Graphite.colors.textDisabled,
    errorLabelColor = Graphite.colors.danger,
    focusedPlaceholderColor = Graphite.colors.textMuted,
    unfocusedPlaceholderColor = Graphite.colors.textMuted,
    disabledPlaceholderColor = Graphite.colors.textDisabled,
    focusedLeadingIconColor = Graphite.colors.textMuted,
    unfocusedLeadingIconColor = Graphite.colors.textMuted,
    focusedTrailingIconColor = Graphite.colors.textMuted,
    unfocusedTrailingIconColor = Graphite.colors.textMuted,
    disabledTrailingIconColor = Graphite.colors.textDisabled,
    errorTrailingIconColor = Graphite.colors.danger,
)
