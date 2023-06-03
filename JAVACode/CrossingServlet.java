package com.railway.web;

import com.railway.model.Crossing;
import com.railway.util.DBUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/crossings")
public class CrossingServlet extends HttpServlet {

	    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

		protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        List<Crossing> crossings = new ArrayList<>();
	        Connection connection = null;
	        Statement statement = null;
	        ResultSet resultSet = null;

	        try {
	            connection = DBUtil.getConnection();
	            statement = connection.createStatement();
	            resultSet = statement.executeQuery("SELECT * FROM crossings");

	            while (resultSet.next()) {
	                int id = resultSet.getInt("id");
	                String name = resultSet.getString("name");
	                boolean status = resultSet.getBoolean("status");

	                Crossing crossing = new Crossing();
	                crossing.setId(id);
	                crossing.setName(name);
	                crossing.setStatus(status);

	                crossings.add(crossing);
	            }

	            request.setAttribute("crossings", crossings);
	            request.getRequestDispatcher("WEB-INF/views/crossing.jsp").forward(request, response);
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (resultSet != null)
	                    resultSet.close();
	                if (statement != null)
	                    statement.close();
	                if (connection != null)
	                    connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String action = request.getParameter("action");

	        if (action != null) {
	            switch (action) {
	                case "add":
	                    addCrossing(request, response);
	                    break;
	                case "update":
	                    updateCrossing(request, response);
	                    break;
	                case "delete":
	                    deleteCrossing(request, response);
	                    break;
	            }
	        }
	    }

	    private void addCrossing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        String name = request.getParameter("name");
	        boolean status = Boolean.parseBoolean(request.getParameter("status"));

	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            connection = DBUtil.getConnection();
	            statement = connection.prepareStatement("INSERT INTO crossings (name, status) VALUES (?, ?)");
	            statement.setString(1, name);
	            statement.setBoolean(2, status);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (statement != null)
	                    statement.close();
	                if (connection != null)
	                    connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        response.sendRedirect(request.getContextPath() + "/crossings");
	    }

	    private void updateCrossing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));
	        String name = request.getParameter("name");
	        boolean status = Boolean.parseBoolean(request.getParameter("status"));

	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            connection = DBUtil.getConnection();
	            statement = connection.prepareStatement("UPDATE crossings SET name = ?, status = ? WHERE id = ?");
	            statement.setString(1, name);
	            statement.setBoolean(2, status);
	            statement.setInt(3, id);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (statement != null)
	                    statement.close();
	                if (connection != null)
	                    connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        response.sendRedirect(request.getContextPath() + "/crossings");
	    }

	    private void deleteCrossing(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        int id = Integer.parseInt(request.getParameter("id"));

	        Connection connection = null;
	        PreparedStatement statement = null;

	        try {
	            connection = DBUtil.getConnection();
	            statement = connection.prepareStatement("DELETE FROM crossings WHERE id = ?");
	            statement.setInt(1, id);
	            statement.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (statement != null)
	                    statement.close();
	                if (connection != null)
	                    connection.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        response.sendRedirect(request.getContextPath() + "/crossings");
	    }
	}


