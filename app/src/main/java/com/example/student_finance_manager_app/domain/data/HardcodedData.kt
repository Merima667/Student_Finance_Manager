package com.example.student_finance_manager_app.domain.data

import com.example.student_finance_manager_app.domain.data.UserProfile

data class CategoryItem(
    val name: String,
    val amount: Double
)

object HardcodedData {
    val defaultUserProfile = UserProfile(
        name = "Merima",
        monthlyBudget = 500.0
    )

    val defaultTransactions = listOf(
        Transaction(
            title = "Stipendija",
            amount = 300.0,
            type = TransactionType.INCOME,
            category = Category.OTHER,
            date = "2026-03-01"
        ),
        Transaction(
            title = "Ručak",
            amount = 8.50,
            type = TransactionType.EXPENSE,
            category = Category.FOOD,
            date = "2026-03-10"
        ),
        Transaction(
            title = "Bus karta",
            amount = 2.0,
            type = TransactionType.EXPENSE,
            category = Category.TRANSPORT,
            date = "2026-03-11"
        ),
        Transaction(
            title = "Udžbenik",
            amount = 35.0,
            type = TransactionType.EXPENSE,
            category = Category.EDUCATION,
            date = "2026-03-12"
        ),
        Transaction(
            title = "Kafa",
            amount = 3.0,
            type = TransactionType.EXPENSE,
            category = Category.FOOD,
            date = "2026-03-13"
        ),
        Transaction(
            title = "Taksi",
            amount = 5.0,
            type = TransactionType.EXPENSE,
            category = Category.TRANSPORT,
            date = "2026-03-14"
        ),
        Transaction(
            title = "Džeparac",
            amount = 50.0,
            type = TransactionType.INCOME,
            category = Category.OTHER,
            date = "2026-03-15"
        ),
        Transaction(
            title = "Bioskop",
            amount = 10.0,
            type = TransactionType.EXPENSE,
            category = Category.ENTERTAINMENT,
            date = "2026-03-16"
        ),
        Transaction(
            title = "Ljekarski pregled",
            amount = 20.0,
            type = TransactionType.EXPENSE,
            category = Category.HEALTH,
            date = "2026-03-17"
        ),
        Transaction(
            title = "Stipendija 2",
            amount = 200.0,
            type = TransactionType.INCOME,
            category = Category.OTHER,
            date = "2026-03-18"
        )
    )

    val defaultCategories = listOf(
        CategoryItem("Hrana", 11.50),
        CategoryItem("Transport", 7.0),
        CategoryItem("Obrazovanje", 35.0),
        CategoryItem("Zabava", 10.0),
        CategoryItem("Zdravlje", 20.0),
        CategoryItem("Ostalo", 550.0)
    )
}