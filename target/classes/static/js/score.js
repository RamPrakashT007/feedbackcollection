const urlParams = new URLSearchParams(window.location.search);
const userId = urlParams.get('userId');
const totalScore = urlParams.get('totalScore');
const totalTimeTaken = urlParams.get('totalTimeTaken');
const quizSummaryId = Number(urlParams.get('quizSummaryId')); // Convert to number

// Display results
function displayResults() {
    if (totalScore && totalTimeTaken && userId) {
        document.getElementById("scoreDisplay").textContent = totalScore;
        document.getElementById("timeTaken").textContent = `${totalTimeTaken} seconds`;
        document.getElementById("userIdDisplay").textContent = userId;
    } else {
        console.error("Missing query parameters for results display.");
    }
}

function saveAdminData(userId, quizSummaryId) {
    if (!userId || !quizSummaryId) {
        alert("User ID or Quiz Summary ID is missing!");
        return;
    }

    // First, check if the admin data already exists
    fetch(`https://feedbackcollection-5wlz.onrender.com/api/check-admin-data?userId=${userId}&quizSummaryId=${quizSummaryId}`)
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to check admin data");
            }
            return response.json();
        })
        .then(data => {
            if (data.exists) {
                // If the data already exists, show an alert
                alert("Admin data already exists in the database!");
            } else {
                // If the data does not exist, save it
                saveAdminDataToBackend(userId, quizSummaryId);
            }
        })
        .catch(error => {
            console.error("Error checking admin data:", error);
            alert("Failed to check admin data. Please try again.");
        });
}

function saveAdminDataToBackend(userId, quizSummaryId) {
    fetch("https://feedbackcollection-5wlz.onrender.com/api/save-admin-data", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
            userId: userId,
            quizSummaryId: quizSummaryId
        })
    })
    .then(response => {
        if (!response.ok) {
            return response.json().then(errorData => {
                throw new Error(errorData.error || "Failed to save admin data");
            });
        }
        return response.json();
    })
    .then(data => {
        console.log("Admin data saved successfully:", data);
        alert("Admin data saved successfully!");
    })
    .catch(error => {
        console.error("Error saving admin data:", error);
        alert(error.message);
    });
}
// Call the displayResults function when the page loads
window.onload = function () {
    displayResults();

    // Add event listener for the Save Response button
    document.getElementById("saveResponseBtn").addEventListener("click", function () {
        if (userId && quizSummaryId) {
            saveAdminData(userId, quizSummaryId);
        } else {
            alert("User ID or Quiz Summary ID is missing!");
        }
    });

    // Add event listener for the Restart Quiz button
    document.getElementById("restartBtn").addEventListener("click", function () {
        localStorage.removeItem('quizStartTime'); // Clear the timer state
        window.location.href = "/"; // Redirect to the home page
    });
};