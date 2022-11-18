package com.example.wordsapp

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import junit.framework.Assert.assertEquals
import org.junit.Test

class NavigationTests {

    @Test
    fun navigate_to_words_nav_component() {
        // Adiciona instância de teste do controlador de navegação

        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        // Dependência específica de fragmentos
        // Especifica que se quer abrir o LetterListFragment, transmitindo o tema
        // do app para que o componente da IU saiba qual usar e o teste não falhe
        val letterListScenario = launchFragmentInContainer<LetterListFragment>(themeResId =
        R.style.Theme_Words)

        // Declara explicitamente qual gráfico de navegação o controlador
        // deve usar para o fragmento aberto
        letterListScenario.onFragment { fragment ->

            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        // Aciona o evento que solicita a navegação
        onView(withId(R.id.recycler_view))
            .perform(
                RecyclerViewActions
               .actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))

        // Em vez de procurar um componente de IU que é mostrado em uma tela específica,
        // pode-se conferir se o destino do controlador de navegação atual tem o ID do
        // fragmento esperado. Essa abordagem é mais confiável.
        assertEquals(navController.currentDestination?.id, R.id.wordListFragment)
    }
}