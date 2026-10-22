package ao.co.isptec.aplm.exercise_3;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

/**
 * Exercício III – Aplicação de Lista de Tarefas
 *
 * A actividade mostra:
 *  - Um EditText para o utilizador escrever uma nova tarefa
 *  - Um ListView que mostra todas as tarefas adicionadas
 *
 * Quando o utilizador prime ENTER no EditText:
 *  - A tarefa é adicionada ao TOPO da lista (índice 0)
 *  - O EditText é limpo para a próxima entrada
 */
public class MainActivity extends AppCompatActivity {

    // Lista que armazena as tarefas em memória
    private ArrayList<String> taskList;

    // Adapter que liga a lista ao ListView (padrão MVC no Android)
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inicializar a lista de tarefas (começa vazia)
        taskList = new ArrayList<>();

        // 2. Obter referência ao ListView pelo seu ID
        ListView listView = findViewById(R.id.listViewTasks);

        /*
         * 3. Criar o ArrayAdapter
         *
         * O ArrayAdapter é o "intermediário" entre os dados (taskList)
         * e a vista (ListView). Cada vez que a lista muda, o adapter
         * actualiza o ListView automaticamente.
         *
         * Parâmetros:
         *  - this          → contexto (a actividade actual)
         *  - simple_list_item_1 → layout padrão para cada item da lista
         *  - taskList      → os dados a mostrar
         */
        adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                taskList
        );

        // 4. Ligar o adapter ao ListView
        listView.setAdapter(adapter);

        // 5. Obter referência ao EditText
        EditText editText = findViewById(R.id.editTextTask);

        /*
         * 6. Definir o listener para capturar a tecla ENTER
         *
         * setOnKeyListener captura eventos de teclado na view.
         * Quando o utilizador prime ENTER (KEYCODE_ENTER) e larga a tecla
         * (ACTION_DOWN), a tarefa é adicionada à lista.
         */
        editText.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                // Verificar se foi a tecla ENTER pressionada
                if (event.getAction() == KeyEvent.ACTION_DOWN
                        && keyCode == KeyEvent.KEYCODE_ENTER) {

                    // Ler o texto introduzido (remover espaços desnecessários)
                    String task = editText.getText().toString().trim();

                    // Só adicionar se o campo não estiver vazio
                    if (!task.isEmpty()) {

                        // Adicionar a nova tarefa ao TOPO da lista (índice 0)
                        taskList.add(0, task);

                        // Notificar o adapter que os dados mudaram
                        // → o ListView actualiza-se automaticamente
                        adapter.notifyDataSetChanged();

                        // Limpar o EditText para a próxima tarefa
                        editText.setText("");
                    }

                    // Retornar true indica que o evento foi tratado
                    return true;
                }

                // Retornar false para outros eventos (são tratados normalmente)
                return false;
            }
        });
    }
}
