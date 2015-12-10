using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.OleDb;
using System.Data;
using DWR.Model;


namespace DWR
{
    class DataBaseConnection : IDisposable
    {
        private OleDbConnection Connection;
        private OleDbTransaction Transaction;

        public DataBaseConnection(String connectionString)
        {
            this.Connection = new OleDbConnection(connectionString);

            this.Connection.Open();
        }


        public IDataReader ExecuteSqlCommand(String sqlCommand)
        {
            IDbCommand command = new OleDbCommand(sqlCommand, this.Connection);

            //command.Transaction = this.Transaction;

            return command.ExecuteReader();
        }


        public int ExecuteNonQuery(String sqlCommand)
        {
            IDbCommand command = new OleDbCommand(sqlCommand, this.Connection);

            command.Transaction = this.Transaction;

            return command.ExecuteNonQuery();
        }


        public void ExecuteSqlProcedure(String procedureName, IList<String> parameterNames, IList<OleDbType> parameterTypes, IList<ParameterDirection> parameterDirections, IList<Object> parameterValues)
        {
            OleDbCommand command = new OleDbCommand(procedureName, this.Connection);
            int numOfParameters = parameterNames.Count;

            command.Transaction = Transaction;
            command.CommandType = CommandType.StoredProcedure;

            for (int i = 0; i < numOfParameters; i++)
            {
                command.Parameters.Add(parameterNames[i], parameterTypes[i]);
                command.Parameters[parameterNames[i]].Direction = parameterDirections[i];
                command.Parameters[parameterNames[i]].Value = parameterValues[i];
            }

            try
            {
                command.ExecuteNonQuery();

                if (command.Transaction != null)
                {
                    command.Transaction.Commit();
                }
            }
            catch (OleDbException error)
            {
                System.Windows.MessageBox.Show(error.Message, null, System.Windows.MessageBoxButton.OK, System.Windows.MessageBoxImage.Error);

                if (command.Transaction != null)
                {
                    command.Transaction.Rollback();
                }
            }
        }


        public void BeginTransaction(IsolationLevel isolationLevel)
        {
            this.Transaction = this.Connection.BeginTransaction(isolationLevel);
        }


        public void CommitTransaction()
        {
            this.Transaction.Commit();
            this.Transaction = null;
        }


        public void RollbackTransaction()
        {
            this.Transaction.Rollback();
            this.Transaction = null;
        }


        public bool TransactionActionActive()
        {
            return Transaction == null ? false : true;
        }


        public void Dispose()
        {
            this.Connection.Dispose();
        }
    }
    
}
