async function receiveJobData() {
  let response = await fetch("http://localhost:8080/api");

  if (!response.ok) {
    throw new Error(`ERROR EN LA PETICIÓN AL SERVIDOR. STATUS: ${response.status}`);
  }
  else console.log(response.status)

  let data = await response.json(); // Una vez ya con la respuesta, ya pintamos los datos.

  const body = document.querySelector("tbody");

  body.innerHTML = '';

  completeTable(data, body);
}

function completeTable(json, fatherElement) {

  json.forEach(object => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
            <td><a href="${object.urlVacante}" target="_blank">Ver enlace</a></td>
            <td>${object.trabajo || 'No especificado'}</td>
            <td>${object.localidad}</td>
            <td>${object.fechaAplicacion}</td>
            <td>${object.horaAplicacion}</td>
            <td>${object.estado}</td>
            <td>${object.fechaPublicacion}</td>
            <td>${object.salario}</td>
        `;
    fatherElement.appendChild(tr);
  });

}


