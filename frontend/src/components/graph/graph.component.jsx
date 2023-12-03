import { Line } from "react-chartjs-2";
import "chart.js/auto";

const Graph = ({ progressData, className }) => {
  if (!progressData) {
    return <div>Loading...</div>;
  }

  const labels = progressData.map((data) => data.date);
  const bmiData = progressData.map((data) => data.bmi);

  const data = {
    labels,
    datasets: [
      {
        label: "BMI",
        data: bmiData,
        borderColor: "rgba(255, 99, 132, 1)",
        backgroundColor: "rgba(255, 99, 132, 0.5)",
        pointStyle: "circle",
        pointRadius: 10,
        pointHoverRadius: 15,
      },
    ],
  };

  const options = {
    scales: {
      y: {
        beginAtZero: true,
      },
    },
  };

  return <Line data={data} options={options} className={className} />;
};

export default Graph;
