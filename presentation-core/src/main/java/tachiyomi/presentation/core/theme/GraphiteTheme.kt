// From the unified-ui skill (Graphite design system), assets/compose/GraphiteTheme.kt
// Mirrors the skill's tokens.json; change values there first, never here.

package tachiyomi.presentation.core.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.LocalRippleConfiguration
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RippleConfiguration
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Immutable
data class GraphiteColors(
    val canvas: Color,
    val surface: Color,
    val raised: Color,
    val hover: Color,
    val pressed: Color,
    val borderSubtle: Color,
    val border: Color,
    val borderStrong: Color,
    val text: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val textDisabled: Color,
    val accent: Color,
    val accentFill: Color,
    val accentFillHover: Color,
    val accentFillPressed: Color,
    val accentSubtle: Color,
    val onAccent: Color,
    val success: Color,
    val successSubtle: Color,
    val warning: Color,
    val warningSubtle: Color,
    val danger: Color,
    val dangerFill: Color,
    val dangerSubtle: Color,
    val onDanger: Color,
    val scrim: Color,
    val shadow: Color,
    val isDark: Boolean,
)

val GraphiteDark = GraphiteColors(
    canvas = Color(0xFF0E0F11),
    surface = Color(0xFF17181B),
    raised = Color(0xFF1D1F23),
    hover = Color(0xFF23252A),
    pressed = Color(0xFF2A2D33),
    borderSubtle = Color(0xFF26282D),
    border = Color(0xFF33363C),
    borderStrong = Color(0xFF474B53),
    text = Color(0xFFE6E7EA),
    textSecondary = Color(0xFFA0A3AB),
    textMuted = Color(0xFF8B8E96),
    textDisabled = Color(0xFF6C6F77),
    accent = Color(0xFF7B89FF),
    accentFill = Color(0xFF4F5EF5),
    accentFillHover = Color(0xFF5B6CFF),
    accentFillPressed = Color(0xFF4452DB),
    accentSubtle = Color(0xFF1B1F3D),
    onAccent = Color(0xFFFFFFFF),
    success = Color(0xFF3FB97A),
    successSubtle = Color(0xFF12281D),
    warning = Color(0xFFE5A63B),
    warningSubtle = Color(0xFF2C2210),
    danger = Color(0xFFF0566A),
    dangerFill = Color(0xFFD0364B),
    dangerSubtle = Color(0xFF30141A),
    onDanger = Color(0xFFFFFFFF),
    scrim = Color(0x99000000),
    shadow = Color(0x66000000),
    isDark = true,
)

val GraphiteLight = GraphiteColors(
    canvas = Color(0xFFFAFAFB),
    surface = Color(0xFFFFFFFF),
    raised = Color(0xFFFFFFFF),
    hover = Color(0xFFF0F1F3),
    pressed = Color(0xFFE6E7EA),
    borderSubtle = Color(0xFFE6E7EA),
    border = Color(0xFFD5D7DC),
    borderStrong = Color(0xFFB4B7BE),
    text = Color(0xFF17181B),
    textSecondary = Color(0xFF50535B),
    textMuted = Color(0xFF6C6F77),
    textDisabled = Color(0xFF878B93),
    accent = Color(0xFF4352E0),
    accentFill = Color(0xFF4F5EF5),
    accentFillHover = Color(0xFF4452DB),
    accentFillPressed = Color(0xFF3A46C2),
    accentSubtle = Color(0xFFECEEFF),
    onAccent = Color(0xFFFFFFFF),
    success = Color(0xFF17753F),
    successSubtle = Color(0xFFE6F5EC),
    warning = Color(0xFF946200),
    warningSubtle = Color(0xFFFBF1DC),
    danger = Color(0xFFBC283E),
    dangerFill = Color(0xFFD0364B),
    dangerSubtle = Color(0xFFFCE9EC),
    onDanger = Color(0xFFFFFFFF),
    scrim = Color(0x66000000),
    shadow = Color(0x2917181B),
    isDark = false,
)

/** Spacing scale. Nothing in between these values. */
object GSpace {
    val s1 = 2.dp
    val s2 = 4.dp
    val s3 = 8.dp
    val s4 = 12.dp
    val s5 = 16.dp
    val s6 = 24.dp
    val s7 = 32.dp
    val s8 = 48.dp
}

object GRadius {
    val tag = RoundedCornerShape(4.dp)
    val control = RoundedCornerShape(6.dp)
    val card = RoundedCornerShape(8.dp)
    val dialog = RoundedCornerShape(12.dp)
    val sheet = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp)
}

/** Touch-density sizes. */
object GSize {
    val controlSm = 36.dp
    val control = 44.dp
    val controlLg = 52.dp
    val controlPadX = 16.dp
    val row = 52.dp
    val icon = 20.dp
    val minTarget = 48.dp
    val topBar = 56.dp
    val bottomBar = 64.dp
    val pagePad = 16.dp
    val border = 1.dp
}

object GMotion {
    val fast = 120
    val base = 180
    val slow = 260
    val easing = androidx.compose.animation.core.CubicBezierEasing(0.2f, 0f, 0f, 1f)
}

/**
 * Type roles (touch density). The font families are passed in by the app so
 * this file doesn't depend on its generated `R` class:
 *
 * val Inter = FontFamily(
 *     Font(R.font.inter_regular, FontWeight.Normal),
 *     Font(R.font.inter_medium, FontWeight.Medium),
 *     Font(R.font.inter_semibold, FontWeight.SemiBold),
 * )
 * val JetBrainsMono = FontFamily(Font(R.font.jetbrains_mono_regular))
 */
@Immutable
data class GraphiteType(
    val display: TextStyle,
    val title: TextStyle,
    val heading: TextStyle,
    val body: TextStyle,
    val label: TextStyle,
    val small: TextStyle,
    val caption: TextStyle,
    val mono: TextStyle,
)

fun graphiteType(sans: FontFamily, mono: FontFamily): GraphiteType {
    fun s(size: Int, line: Int, weight: FontWeight, family: FontFamily = sans) = TextStyle(
        fontFamily = family,
        fontSize = size.sp,
        lineHeight = line.sp,
        fontWeight = weight,
        letterSpacing = 0.sp,
    )
    return GraphiteType(
        display = s(28, 34, FontWeight.SemiBold),
        title = s(20, 28, FontWeight.SemiBold),
        heading = s(17, 24, FontWeight.SemiBold),
        body = s(15, 22, FontWeight.Normal),
        label = s(15, 22, FontWeight.Medium),
        small = s(13, 18, FontWeight.Normal),
        caption = s(12, 16, FontWeight.Medium),
        mono = s(13, 20, FontWeight.Normal, mono),
    )
}

private val LocalGraphiteColors = staticCompositionLocalOf { GraphiteDark }
private val LocalGraphiteType = staticCompositionLocalOf<GraphiteType> {
    error("GraphiteTheme not applied")
}

object Graphite {
    val colors: GraphiteColors
        @Composable @ReadOnlyComposable
        get() = LocalGraphiteColors.current
    val type: GraphiteType
        @Composable @ReadOnlyComposable
        get() = LocalGraphiteType.current
}

@Composable
fun GraphiteTheme(
    sans: FontFamily,
    mono: FontFamily,
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val c = if (darkTheme) GraphiteDark else GraphiteLight
    val t = graphiteType(sans, mono)

    val base = if (darkTheme) darkColorScheme() else lightColorScheme()
    val scheme = base.copy(
        primary = c.accentFill,
        onPrimary = c.onAccent,
        primaryContainer = c.accentSubtle,
        onPrimaryContainer = c.accent,
        secondary = c.accentFill,
        onSecondary = c.onAccent,
        secondaryContainer = c.hover,
        onSecondaryContainer = c.text,
        tertiary = c.accentFill,
        onTertiary = c.onAccent,
        background = c.canvas,
        onBackground = c.text,
        surface = c.canvas,
        onSurface = c.text,
        surfaceVariant = c.surface,
        onSurfaceVariant = c.textSecondary,
        surfaceTint = Color.Transparent,
        surfaceContainerLowest = c.canvas,
        surfaceContainerLow = c.surface,
        surfaceContainer = c.surface,
        surfaceContainerHigh = c.raised,
        surfaceContainerHighest = c.hover,
        outline = c.border,
        outlineVariant = c.borderSubtle,
        error = c.danger,
        onError = c.onDanger,
        errorContainer = c.dangerSubtle,
        onErrorContainer = c.danger,
        scrim = c.scrim,
        inverseSurface = c.text,
        inverseOnSurface = c.canvas,
        inversePrimary = c.accent,
    )

    val typography = Typography(
        displayLarge = t.display, displayMedium = t.display, displaySmall = t.display,
        headlineLarge = t.display, headlineMedium = t.title, headlineSmall = t.title,
        titleLarge = t.title, titleMedium = t.heading, titleSmall = t.label,
        bodyLarge = t.body, bodyMedium = t.body, bodySmall = t.small,
        labelLarge = t.label, labelMedium = t.caption, labelSmall = t.caption,
    )

    val shapes = Shapes(
        extraSmall = GRadius.tag,
        small = GRadius.control,
        medium = GRadius.card,
        large = GRadius.dialog,
        extraLarge = GRadius.dialog,
    )

    CompositionLocalProvider(
        LocalGraphiteColors provides c,
        LocalGraphiteType provides t,
        LocalRippleConfiguration provides RippleConfiguration(color = c.text),
    ) {
        MaterialTheme(
            colorScheme = scheme,
            typography = typography,
            shapes = shapes,
            content = content,
        )
    }
}

/**
 * Material 3 buttons default to pill shapes and Material paddings, which a
 * theme can't change globally. Pass these to every button:
 *
 * Button(onClick, shape = GButton.shape, colors = GButton.primary(),
 *        contentPadding = GButton.padding, modifier = Modifier.heightIn(min = GSize.control))
 *
 * Better: wrap them once in the app's own GPrimaryButton / GSecondaryButton /
 * GGhostButton composables and use only those.
 */
object GButton {
    val shape = GRadius.control
    val padding = androidx.compose.foundation.layout.PaddingValues(horizontal = 16.dp)

    @Composable
    fun primary(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Graphite.colors.accentFill,
        contentColor = Graphite.colors.onAccent,
        disabledContainerColor = Graphite.colors.hover,
        disabledContentColor = Graphite.colors.textDisabled,
    )

    @Composable
    fun danger(): ButtonColors = ButtonDefaults.buttonColors(
        containerColor = Graphite.colors.dangerFill,
        contentColor = Graphite.colors.onDanger,
        disabledContainerColor = Graphite.colors.hover,
        disabledContentColor = Graphite.colors.textDisabled,
    )

    /** For OutlinedButton. */
    @Composable
    fun secondary(): ButtonColors = ButtonDefaults.outlinedButtonColors(
        contentColor = Graphite.colors.text,
        disabledContentColor = Graphite.colors.textDisabled,
    )

    @Composable
    fun secondaryBorder(): BorderStroke = BorderStroke(GSize.border, Graphite.colors.border)

    /** For TextButton. */
    @Composable
    fun ghost(): ButtonColors = ButtonDefaults.textButtonColors(
        contentColor = Graphite.colors.textSecondary,
        disabledContentColor = Graphite.colors.textDisabled,
    )
}

/** Hairline border for cards and panels. */
@Composable
fun graphiteCardBorder(): BorderStroke = BorderStroke(GSize.border, Graphite.colors.borderSubtle)
