package com.example.student_finance_manager_app.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.student_finance_manager_app.model.Category
import com.example.student_finance_manager_app.model.Transaction
import com.example.student_finance_manager_app.model.TransactionType
import com.example.student_finance_manager_app.model.UserProfile
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

class FinanceViewModel : ViewModel() {
    var userProfile by mutableStateOf(UserProfile(name = "Merima", monthlyBudget = 500.0))
        private set
    var transactions by mutableStateOf(
        listOf(
            Transaction(title = "Stipendija", amount = 300.0, type = TransactionType.INCOME, category = Category.OTHER, date = "2026-03-01"),
            Transaction(title = "Rucak", amount = 8.50, type = TransactionType.EXPENSE, category = Category.FOOD, date = "2026-03-10"),
            Transaction(title = "Bus karta", amount = 2.0, type = TransactionType.EXPENSE, category = Category.TRANSPORT, date = "2026-03-11"),
            Transaction(title = "Udzbenik", amount = 35.0, type = TransactionType.EXPENSE, category = Category.EDUCATION, date = "2026-03-12")
        )
    )
        private set

    val totalIncome: Double get() = transactions.filter {it.type == TransactionType.INCOME }.sumOf { it.amount }
    val totalExpenses: Double get() = transactions.filter {it.type == TransactionType.EXPENSE }.sumOf { it.amount }
    val balance: Double get() = totalIncome - totalExpenses

    var titleInput by mutableStateOf("")
        private set
    var amountInput by mutableStateOf("")
        private set
    var selectedType by mutableStateOf(TransactionType.EXPENSE)
        private set
    var selectedCategory by mutableStateOf(Category.OTHER)
        private set
    var formError by mutableStateOf<String?>(null)
        private set

    fun onTitleChange(value: String) {
        titleInput = value;
        formError = null
    }
    fun onAmountChange(value: String) {
        amountInput = value;
        formError = null
    }
    fun onTypeChange(value: TransactionType) {
        selectedType = value
    }
    fun onCategoryChange(value: Category) {
        selectedCategory = value
    }
    fun addTransaction(): Boolean {
        when {
            titleInput.isBlank() -> {formError = "Naziv ne može biti prazan"; return false}
            amountInput.isBlank() -> {formError = "Iznos ne može biti prazan"; return false}
            amountInput.toDoubleOrNull() == null -> {formError = "Iznos mora biti broj"; return false}
            amountInput.toDouble() <= 0 -> {formError = "Iznos mora biti veći  od 0"; return false}
        }
        val newTransaction = Transaction(
            title = titleInput,
            amount = amountInput.toDouble(),
            type = selectedType,
            category = selectedCategory,
            date = "2026-03-24"
        )
        transactions = transactions + newTransaction
        titleInput = ""
        amountInput = ""
        formError = null
        return true
    }
    fun updateBudget(newBudget: Double) {
        userProfile = userProfile.copy(monthlyBudget = newBudget)
    }
}

