# Fundamentos de UX do MVP

## Direção visual

Azul-marinho cria estrutura e confiança; verde-petróleo destaca ações e sucesso; fundos claros reduzem carga visual; cores semânticas são usadas de forma consistente para status.

## Princípios aplicados

- hierarquia: contexto, título, explicação e ação principal abrem cada tela;
- reconhecimento: navegação persistente, rótulos com ícones e status consistentes;
- ação no contexto: agendamento ao lado da agenda, cadastro sobre a lista e status no cartão;
- feedback imediato: novos registros aparecem e cartões mudam de coluna;
- prevenção: campos tipados, escolhas controladas e validação reforçada na API;
- responsividade: menu adaptável, tabelas condensadas e quadros empilhados no mobile.

## Fluxos centrais

```text
Login → Dashboard → Novo agendamento → Agenda
Clientes → Novo cliente → Cliente disponível
Atendimentos → Iniciar → Em andamento → Concluir
```

Foram considerados contraste, alvos de toque próximos de 40 px, rótulos de formulário, regiões semânticas e navegação que não depende só de cor.

Próxima validação recomendada: teste por teclado/leitor de tela e sessões moderadas com cinco usuários do público-alvo, focando nomenclatura, reagendamento e entendimento dos indicadores.
