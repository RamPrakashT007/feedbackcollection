document.addEventListener("DOMContentLoaded", function () { 
    fetch("http://localhost:5000/get-temp-results")
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json();
        })
        .then(data => {
            console.log("📢 Received temp scores:", data); // Debugging

            const tableBody = document.getElementById("responseTable");
            tableBody.innerHTML = ""; // Clear existing data

            if (!data.length) {
                tableBody.innerHTML = "<tr><td colspan='5' class='text-center'>No responses found</td></tr>";
                return;
            }

            data.forEach(result => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${result.name}</td>
                    <td>${result.email}</td>
                    <td>${result.age}</td>
                    <td>${result.score}</td>
                    <td>${result.timeTaken}</td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => console.error("❌ Error fetching results:", error));
});

